package io.edpn.backend.exploration.application.domain;

import io.edpn.backend.util.Module;

public record SystemCoordinateRequest(String systemName,
                                      Module requestingModule) {
}
