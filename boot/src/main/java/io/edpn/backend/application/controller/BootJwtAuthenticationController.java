package io.edpn.backend.application.controller;

import io.edpn.backend.user.application.controller.DefaultJwtAuthenticationController;
import io.edpn.backend.user.domain.service.JwtTokenService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BootJwtAuthenticationController extends DefaultJwtAuthenticationController {

    public BootJwtAuthenticationController(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, JwtTokenService jwtTokenUtil) {
        super(passwordEncoder, userDetailsService, jwtTokenUtil);
    }
}
