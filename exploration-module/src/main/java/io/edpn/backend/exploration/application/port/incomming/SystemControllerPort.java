package io.edpn.backend.exploration.application.port.incomming;

import io.edpn.backend.exploration.application.dto.SystemDto;

import java.util.List;

public interface SystemControllerPort {

    List<SystemDto> findByNameContaining(String subString, Integer amount);
}
