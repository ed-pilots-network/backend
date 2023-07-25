package io.edpn.backend.application.controller;

import io.edpn.backend.exploration.application.controller.v1.DefaultExplorationModuleController;
import io.edpn.backend.exploration.application.mappers.v1.SystemDtoMapper;
import io.edpn.backend.exploration.domain.usecase.FindSystemsFromSearchbarUseCase;
import io.edpn.backend.trade.application.controller.v1.DefaultTradeModuleController;
import io.edpn.backend.trade.domain.service.v1.FindCommodityMarketInfoService;
import io.edpn.backend.trade.domain.service.v1.FindCommodityService;
import io.edpn.backend.trade.domain.service.v1.LocateCommodityService;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootExplorationModuleController extends DefaultExplorationModuleController {

    public BootExplorationModuleController(FindSystemsFromSearchbarUseCase findSystemsFromSearchbarUseCase, SystemDtoMapper systemDtoMapper) {
        super(findSystemsFromSearchbarUseCase, systemDtoMapper);
    }
}
