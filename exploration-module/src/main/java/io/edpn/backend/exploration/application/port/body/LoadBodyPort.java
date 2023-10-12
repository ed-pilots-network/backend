package io.edpn.backend.exploration.application.port.body;

import io.edpn.backend.exploration.application.domain.Body;

import java.util.Optional;

public interface LoadBodyPort {

    Optional<Body> load(String name);
}
