package io.edpn.backend.exploration.application.port.incomming;

import io.edpn.backend.exploration.application.domain.System;

import java.util.List;

public interface FindSystemsByNameContainingUseCase {

    List<System> findSystemsByNameContaining(String subString, int amount);
}
