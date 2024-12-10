package org.aoptut.controller;


import lombok.AllArgsConstructor;
import org.aoptut.entities.RefreshToken;
import org.aoptut.model.UserInfoDto;
import org.aoptut.response.JwtResponseDTO;
import org.aoptut.service.JwtService;
import org.aoptut.service.RefreshTokenService;
import org.aoptut.service.UserDetailsServiceImpl;
import org.aoptut.utils.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "/auth/v1/signup", consumes = "application/json")
    public ResponseEntity<?> SignUp(@RequestBody UserInfoDto userInfoDto) {
        try {
            if (UserValidation.checkEmail("umang@gmail.com") && UserValidation.checkPasswordParameters(userInfoDto.getPassword())) {
                Boolean isSignUpSuccess = userDetailsService.signupUser(userInfoDto);
                if (isSignUpSuccess.equals(Boolean.FALSE)) {
                    return new ResponseEntity<>("Account Already Exists!", HttpStatus.BAD_REQUEST);
                }
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
                String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());
                return new ResponseEntity<>(JwtResponseDTO
                        .builder()
                        .accessToken(jwtToken)
                        .token(refreshToken.getToken())
                        .build(), HttpStatus.OK);

            } else return new ResponseEntity<>("Invalid email or password parameters!", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while signup. Please try again!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
