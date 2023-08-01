package io.edpn.backend.exploration.adapter.persistence;


import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.port.outgoing.CreateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.SaveSystemPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SystemRepository implements CreateSystemPort, LoadSystemPort, SaveSystemPort {
    @Override
    public System create(String systemName) {
        return null;
    }

    @Override
    public Optional<System> load(String name) {
        return Optional.empty();
    }

    @Override
    public System save(System system) {
        return null;
    }
}
