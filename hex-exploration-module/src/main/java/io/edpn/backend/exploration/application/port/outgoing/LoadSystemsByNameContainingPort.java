package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.exploration.application.domain.System;

import java.util.List;

public interface LoadSystemsByNameContainingPort {

    List<System> load(String name, int amount);

}
