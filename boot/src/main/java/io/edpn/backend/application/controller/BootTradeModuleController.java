package io.edpn.backend.application.controller;

import io.edpn.backend.trade.adapter.web.ValidatedCommodityController;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindValidatedCommodityByNameUseCase;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BootTradeModuleController extends ValidatedCommodityController {
    public BootTradeModuleController(FindAllValidatedCommodityUseCase findAllValidatedCommodityUseCase,
                                     FindValidatedCommodityByNameUseCase findValidatedCommodityByNameUseCase,
                                     FindValidatedCommodityByFilterUseCase findValidatedCommodityByFilterUseCase) {
        super(findAllValidatedCommodityUseCase, findValidatedCommodityByNameUseCase, findValidatedCommodityByFilterUseCase);
    }
}
