package io.edpn.backend.exploration.domain.controller.v1;

import io.edpn.backend.exploration.domain.dto.v1.SystemDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/exploration")
public interface ExplorationModuleController {
    @GetMapping("/system/search-bar")
    List<SystemDto> findSystemsFromSearchBar(@RequestParam(name = "subString", required = true) String subString, @RequestParam(name = "amount", required = false, defaultValue = "10") Integer amount);
}
