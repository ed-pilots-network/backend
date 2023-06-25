package io.edpn.backend.user.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationController {

    @PostMapping("/authenticate")
    ResponseEntity<? extends AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest jsonAuthenticationRequest) throws Exception;

    @PostMapping("/refresh-token")
    ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception;

    interface AuthenticationResponse {
        String getJwt();

        String getRefreshToken();
    }

    interface AuthenticationRequest {
        String getUsername();

        void setUsername(String username);

        String getPassword();

        void setPassword(String password);
    }

    interface RefreshTokenRequest {
        String getRefreshToken();

    }
}
