package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.dto.FindCommodityDTO;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDTO;
import io.edpn.backend.trade.application.dto.mapper.FindCommodityEntityMapper;
import io.edpn.backend.trade.application.dto.mapper.ValidatedCommodityDTOMapper;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindValidatedCommodityByNameUseCase;
import io.edpn.backend.trade.application.usecase.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FindCommodityService implements FindAllValidatedCommodityUseCase, FindValidatedCommodityByNameUseCase, FindValidatedCommodityByFilterUseCase {
    
    private final LoadAllValidatedCommodityPort loadAllValidatedCommodityPort;
    private final LoadValidatedCommodityByNamePort loadValidatedCommodityByNamePort;
    private final LoadValidatedCommodityByFilterPort loadValidatedCommodityByFilterPort;
    private final ValidatedCommodityDTOMapper validatedCommodityDTOMapper;
    private final FindCommodityEntityMapper findCommodityEntityMapper;
    
    
    @Override
    public List<ValidatedCommodityDTO> findAll() {
        return loadAllValidatedCommodityPort
                .loadAll()
                .stream()
                .map(validatedCommodityDTOMapper::map)
                .toList();
    }
    
    @Override
    public Optional<ValidatedCommodityDTO> findByName(String displayName) {
        return loadValidatedCommodityByNamePort
                .loadByName(displayName)
                .map(validatedCommodityDTOMapper::map);
    }
    
    @Override
    public List<ValidatedCommodityDTO> findByFilter(FindCommodityDTO findCommodityRequest) {
        return loadValidatedCommodityByFilterPort
                .loadByFilter(findCommodityEntityMapper.map(findCommodityRequest))
                .stream()
                .map(validatedCommodityDTOMapper::map)
                .toList();
    }
}
