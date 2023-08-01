package io.edpn.backend.exploration.application.port.incomming;

import io.edpn.backend.exploration.application.dto.SystemDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/exploration/system")
public interface SystemControllerPort {

    @GetMapping("/search-bar")
    List<SystemDto> findSystemsFromSearchBar(@RequestParam(name = "subString") String subString, @RequestParam(name = "amount", required = false, defaultValue = "10") Integer amount);
}
