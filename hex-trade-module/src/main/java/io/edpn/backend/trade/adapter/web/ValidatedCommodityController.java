package io.edpn.backend.trade.adapter.web;

import io.edpn.backend.trade.application.dto.FindCommodityDTO;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByNamePort;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindValidatedCommodityByNameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class ValidatedCommodityController implements FindAllValidatedCommodityPort, FindValidatedCommodityByFilterPort, FindValidatedCommodityByNamePort {
    
    private final FindAllValidatedCommodityUseCase findAllValidatedCommodityUseCase;
    private final FindValidatedCommodityByNameUseCase findValidatedCommodityByNameUseCase;
    private final FindValidatedCommodityByFilterUseCase findValidatedCommodityByFilterUseCase;
    
    
    @Override
    @GetMapping("/commodity")
    public List<ValidatedCommodityDTO> findAll() {
        return findAllValidatedCommodityUseCase.findAll();
    }
    
    @Override
    @GetMapping("/commodity/filter")
    public List<ValidatedCommodityDTO> findByFilter(FindCommodityDTO findCommodityRequest) {
        return findValidatedCommodityByFilterUseCase.findByFilter(findCommodityRequest);
    }
    
    @Override
    @GetMapping("/commodity/{displayName}")
    public Optional<ValidatedCommodityDTO> findByName(@PathVariable String displayName) {
        return findValidatedCommodityByNameUseCase.findByName(displayName);
    }
}
