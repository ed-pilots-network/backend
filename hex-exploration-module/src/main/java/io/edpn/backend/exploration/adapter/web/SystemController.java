package io.edpn.backend.exploration.adapter.web;

import io.edpn.backend.exploration.application.dto.SystemDto;
import io.edpn.backend.exploration.application.port.incomming.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.application.port.incomming.SystemControllerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SystemController implements SystemControllerPort {

    final FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase;

    @Override
    public List<SystemDto> findSystemsFromSearchBar(String subString, Integer amount) {
        return findSystemsFromSearchbarUseCase.findSystemsFromSearchBar(subString, amount);
    }
}
