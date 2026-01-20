local http = require "resty.http"
local cjson = require "cjson.safe"

local AuthPingHandler = {
  VERSION = "1.1.0",
  PRIORITY = 1000,
}

function AuthPingHandler:access(conf)
  local path = kong.request.get_path()

  -- Public endpoints
  if path == "/auth/v1/login"
     or path == "/auth/v1/signup"
     or path == "/auth/v1/refreshToken" then
    return
  end

  local access_token = kong.request.get_header("Authorization")
  local refresh_token = kong.request.get_header("X-Refresh-Token")

  if not access_token then
    return kong.response.exit(401, { message = "Missing access token" })
  end

  local client = http.new()
  client:set_timeout(2000)

  -- 1️⃣ Try ping with access token
  local function ping(token)
    return client:request_uri(conf.auth_service_url, {
      method = "GET",
      headers = {
        ["Authorization"] = token
      }
    })
  end

  local res, err = ping(access_token)

  -- 2️⃣ If access token valid → pass
  if res and res.status == 200 then
    kong.service.request.set_header("x-user-id", res.body)
    return
  end

  -- 3️⃣ Access token expired → try refresh
  if not refresh_token then
    return kong.response.exit(401, { message = "Access token expired" })
  end

  local refresh_res, refresh_err = client:request_uri(
    "http://host.docker.internal:9898/auth/v1/refreshToken",
    {
      method = "POST",
      body = cjson.encode({ token = refresh_token }),
      headers = {
        ["Content-Type"] = "application/json"
      }
    }
  )

  if not refresh_res or refresh_res.status ~= 200 then
    return kong.response.exit(401, { message = "Refresh token invalid" })
  end

  local refresh_body = cjson.decode(refresh_res.body)
  local new_access_token = "Bearer " .. refresh_body.accessToken

  -- 4️⃣ Retry ping with new token
  local retry_res = ping(new_access_token)

  if not retry_res or retry_res.status ~= 200 then
    return kong.response.exit(401, { message = "Unauthorized" })
  end

  -- 5️⃣ Success → inject headers
  kong.service.request.set_header("Authorization", new_access_token)
  kong.service.request.set_header("x-user-id", retry_res.body)
end

return AuthPingHandler
