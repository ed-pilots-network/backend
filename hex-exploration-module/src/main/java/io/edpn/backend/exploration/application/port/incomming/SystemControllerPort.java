package io.edpn.backend.exploration.application.port.incomming;

import io.edpn.backend.exploration.application.dto.SystemDto;

import java.util.List;

public interface SystemControllerPort {

    List<SystemDto> findSystemsFromSearchBar(String subString, Integer amount);
}
