package io.edpn.backend.user.application.dto;

import io.edpn.backend.user.domain.controller.AuthenticationController;
import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder
public class AuthenticationResponse implements AuthenticationController.AuthenticationResponse {
    String jwt;
    String refreshToken;
}
