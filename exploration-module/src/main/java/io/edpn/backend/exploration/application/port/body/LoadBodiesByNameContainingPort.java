package io.edpn.backend.exploration.application.port.body;

import io.edpn.backend.exploration.application.domain.System;

import java.util.List;

public interface LoadBodiesByNameContainingPort {

    List<System> loadByNameContaining(String name, int amount);

}
