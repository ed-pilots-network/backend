package io.edpn.backend.exploration.application.domain;

import io.edpn.backend.util.Module;

public record SystemEliteIdRequest(String systemName,
                                   Module requestingModule) {
}
