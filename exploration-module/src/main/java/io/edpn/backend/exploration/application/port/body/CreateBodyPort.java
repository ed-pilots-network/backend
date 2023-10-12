package io.edpn.backend.exploration.application.port.body;

import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

public interface CreateBodyPort {

    Body create(String bodyName) throws DatabaseEntityNotFoundException;
}
