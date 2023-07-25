package io.edpn.backend.exploration.domain.usecase;

import io.edpn.backend.exploration.domain.model.System;
import java.util.List;

public interface FindSystemsFromSearchbarUseCase {

    public List<System> findSystemsFromSearchBar(String name, int amount);
}
