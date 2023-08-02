package io.edpn.backend.exploration.adapter.web;

import io.edpn.backend.exploration.application.dto.SystemDto;
import io.edpn.backend.exploration.application.port.incomming.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.exploration.application.port.incomming.SystemControllerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/exploration/system")
public class SystemController implements SystemControllerPort {

    final FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase;

    @Override
    @GetMapping("/search-bar")
    public List<SystemDto> findSystemsFromSearchBar(@RequestParam(name = "subString") String subString, @RequestParam(name = "amount", required = false, defaultValue = "10") Integer amount) {
        return findSystemsFromSearchbarUseCase.findSystemsFromSearchBar(subString, amount);
    }
}