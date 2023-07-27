package io.edpn.backend.application.controller;

import io.edpn.backend.exploration.application.controller.v1.DefaultExplorationModuleController;
import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootExplorationModuleController extends DefaultExplorationModuleController {

    public BootExplorationModuleController(FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase) {
        super(findSystemsFromSearchbarUseCase);
    }
}
