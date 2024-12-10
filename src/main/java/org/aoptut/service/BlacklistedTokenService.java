package org.aoptut.service;

import org.aoptut.entities.BlackListedToken;
import org.aoptut.entities.RefreshToken;
import org.aoptut.repository.BlackListedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlacklistedTokenService {

    @Autowired
    BlackListedTokenRepository blackListedTokenRepository;

    public BlackListedToken blacklistToken(String token) {
        BlackListedToken accessToken;
        accessToken = findByToken(token);
        if(accessToken == null){
            accessToken = BlackListedToken.builder().token(token).build();
            return blackListedTokenRepository.save(accessToken);
        }
        return accessToken;
    }

    public Boolean isTokenBlackListed(String token){
        return Objects.nonNull(findByToken(token));
    }
    private BlackListedToken findByToken(String token){
        return blackListedTokenRepository.findByToken(token);
    }

}
