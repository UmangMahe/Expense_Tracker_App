package org.aoptut.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.aoptut.entities.BlackListedToken;
import org.aoptut.entities.RefreshToken;
import org.aoptut.repository.BlackListedTokenRepository;
import org.aoptut.repository.RefreshTokenRepository;
import org.aoptut.repository.UserRepository;
import org.aoptut.service.BlacklistedTokenService;
import org.aoptut.service.JwtService;
import org.aoptut.service.RefreshTokenService;
import org.aoptut.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private BlacklistedTokenService blacklistedTokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Example: Clear JWT from headers or blacklist the token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            BlackListedToken blackListedToken = blacklistedTokenService.blacklistToken(jwt);
            System.out.println("Invalidated token " + blackListedToken.getToken());
        }

        response.setHeader("Authorization", "");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}