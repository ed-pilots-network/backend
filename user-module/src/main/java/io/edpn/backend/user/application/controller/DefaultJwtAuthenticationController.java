package io.edpn.backend.user.application.controller;


import io.edpn.backend.user.domain.controller.AuthenticationController;
import io.edpn.backend.user.domain.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class DefaultJwtAuthenticationController implements AuthenticationController {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenUtil;

    @Override
    public ResponseEntity<? extends AuthenticationResponse> createAuthenticationToken(AuthenticationRequest jsonAuthenticationRequest) throws BadCredentialsException {
        try {
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(jsonAuthenticationRequest.getUsername());
            if (passwordEncoder.matches(jsonAuthenticationRequest.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Incorrect username or password");
            }

            var response = io.edpn.backend.user.application.dto.AuthenticationResponse.builder()
                    .jwt(jwtTokenUtil.generateToken(userDetails))
                    .refreshToken(jwtTokenUtil.generateRefreshToken(userDetails))
                    .build();

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException unfe) {
            throw new BadCredentialsException("Incorrect username or password", unfe);
        }
    }

    @Override
    public ResponseEntity<? extends AuthenticationResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = jwtTokenUtil.extractUsername(refreshToken);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.validateToken(refreshToken, userDetails)) {
            var response = io.edpn.backend.user.application.dto.AuthenticationResponse.builder()
                    .jwt(jwtTokenUtil.generateToken(userDetails))
                    .refreshToken(refreshToken) // do not send a new refresh token, need to log in again after 24 hours
                    .build();

            return ResponseEntity.ok(response);
        } else {
            throw new Exception("Invalid refresh token");
        }
    }
}
