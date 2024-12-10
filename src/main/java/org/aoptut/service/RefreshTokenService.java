package org.aoptut.service;


import org.aoptut.entities.RefreshToken;
import org.aoptut.entities.UserInfo;
import org.aoptut.repository.RefreshTokenRepository;
import org.aoptut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String userName) {
        UserInfo userInfoExtracted = userRepository.findByUsername(userName);
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(60000))
                .build();
        return refreshTokenRepository.save(refreshToken);

    }

    public RefreshToken verifyRefreshToken(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            try {
                refreshTokenRepository.delete(token);
                return null;
            } catch (Exception ex) {
                throw new RuntimeException("Refresh Token is not in the DB!. Fatal error");
            }
        } else return token;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
