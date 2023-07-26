package io.edpn.backend.exploration.domain.controller.v1;

import io.edpn.backend.exploration.application.dto.v1.SystemDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/exploration")
public interface ExplorationModuleController {
    @GetMapping("/system/search-bar")
    List<SystemDTO> findSystemsFromSearchBar(@RequestParam(name = "subString", required = true) String subString, @RequestParam(name = "amount", required = false, defaultValue = "10") Integer amount);
}
