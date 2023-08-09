package io.edpn.backend.trade.adapter.web;

import io.edpn.backend.trade.application.dto.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDto;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByNameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class ValidatedCommodityController {

    private final FindAllValidatedCommodityUseCase findAllValidatedCommodityUseCase;
    private final FindValidatedCommodityByNameUseCase findValidatedCommodityByNameUseCase;
    private final FindValidatedCommodityByFilterUseCase findValidatedCommodityByFilterUseCase;


    @GetMapping("/commodity")
    public List<ValidatedCommodityDto> findAll() {
        return findAllValidatedCommodityUseCase.findAll();
    }

    @GetMapping("/commodity/filter")
    public List<ValidatedCommodityDto> findByFilter(FindCommodityFilterDto findCommodityRequest) {
        return findValidatedCommodityByFilterUseCase.findByFilter(findCommodityRequest);
    }

    @GetMapping("/commodity/{displayName}")
    public Optional<ValidatedCommodityDto> findByName(@PathVariable String displayName) {
        return findValidatedCommodityByNameUseCase.findByName(displayName);
    }
}
