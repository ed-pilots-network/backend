package io.edpn.backend.exploration.domain.usecase;

import io.edpn.backend.exploration.domain.model.System;
import java.util.List;

public interface FindSystemsFromSearchbarUseCase {

    List<System> findSystemsFromSearchBar(String subString, int amount);
}
