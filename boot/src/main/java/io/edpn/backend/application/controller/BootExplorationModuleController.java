package io.edpn.backend.application.controller;

import io.edpn.backend.exploration.adapter.web.SystemController;
import io.edpn.backend.exploration.application.port.incoming.FindSystemsFromSearchbarUseCase;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootExplorationModuleController extends SystemController {

    public BootExplorationModuleController(FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase) {
        super(findSystemsFromSearchbarUseCase);
    }
}
