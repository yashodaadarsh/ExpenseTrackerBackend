package com.adarsh.service;

import com.adarsh.entities.RefreshToken;
import com.adarsh.entities.UserInfo;
import com.adarsh.repository.RefreshTokenRepository;
import com.adarsh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public RefreshToken createRefreshToken(String username) {

        UserInfo user = userRepository.findByUsername(username);

        RefreshToken refreshToken = refreshTokenRepository
                .findByUserInfo(user)
                .orElse(
                        RefreshToken.builder()
                                .userInfo(user)
                                .build()
                );

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if( token.getExpiryDate().compareTo(Instant.now()) < 0 ){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh Token is expired. Please make a new login...!");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token){
        System.out.println("Checking the token in database := " + token);
        return refreshTokenRepository.findByToken(token);
    }
}
