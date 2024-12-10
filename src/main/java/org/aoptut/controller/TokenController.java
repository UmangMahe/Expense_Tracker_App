package org.aoptut.controller;


import lombok.AllArgsConstructor;
import org.aoptut.entities.RefreshToken;
import org.aoptut.entities.UserInfo;
import org.aoptut.request.AuthRequestDTO;
import org.aoptut.request.RefreshTokenRequestDTO;
import org.aoptut.response.JwtResponseDTO;
import org.aoptut.service.JwtService;
import org.aoptut.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@AllArgsConstructor
@RestController
public class TokenController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final RefreshTokenService refreshTokenService;

    @Autowired
    private final JwtService jwtService;

    @GetMapping(value = "/")
    public ResponseEntity<?> LandingPage(){
        return new ResponseEntity<String>("You can now access this", HttpStatus.OK);
    }

    @PostMapping(value = "auth/v1/login", consumes = "application/json")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            String jwtToken = jwtService.GenerateToken(authRequestDTO.getUsername());
            return new ResponseEntity<>(JwtResponseDTO.builder()
                    .accessToken(jwtToken)
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Error while login, Please try again!", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @PostMapping("auth/v1/refreshToken")
    public ResponseEntity<?> GetRefreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {

        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getToken());
        if (Objects.nonNull(refreshTokenService.verifyRefreshToken(refreshToken))) {
            UserInfo userInfo = refreshToken.getUserInfo();
            String accessToken = jwtService.GenerateToken(userInfo.getUsername());

            return new ResponseEntity<>(JwtResponseDTO.builder()
                    .accessToken(accessToken)
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(refreshToken.getToken() + " Refresh token is expired, please login again", HttpStatus.BAD_REQUEST);
        }
    }

}
