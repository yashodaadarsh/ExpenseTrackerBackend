package com.adarsh.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    public static final String SECRET = "aX8hKp0bL9tQ2rF5sD7uV1yE3wZ6cG4nM8oR2jU5lB7vH1zN0kC3pT6xY9aW4dE7";

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token , Function<Claims,T> claimsResolver ){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractDate(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        return extractDate(token).before(new Date());
    }

    public Boolean validateToken(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String createToken(Map<String,Object> claims , String username ){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()) )
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15 ))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String GenerateToken(String username) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }
}
