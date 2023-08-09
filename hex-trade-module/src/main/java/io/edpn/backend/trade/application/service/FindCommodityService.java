package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.dto.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.ValidatedCommodityDto;
import io.edpn.backend.trade.application.dto.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.mapper.PersistenceFindCommodityMapper;
import io.edpn.backend.trade.application.dto.mapper.ValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByNameUseCase;
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
    private final ValidatedCommodityDtoMapper validatedCommodityDTOMapper;
    private final FindCommodityFilterDtoMapper findCommodityFilterDtoMapper;
    private final PersistenceFindCommodityMapper persistenceFindCommodityMapper;


    @Override
    public List<ValidatedCommodityDto> findAll() {
        return loadAllValidatedCommodityPort
                .loadAll()
                .stream()
                .map(validatedCommodityDTOMapper::map)
                .toList();
    }

    @Override
    public Optional<ValidatedCommodityDto> findByName(String displayName) {
        return loadValidatedCommodityByNamePort
                .loadByName(displayName)
                .map(validatedCommodityDTOMapper::map);
    }

    @Override
    public List<ValidatedCommodityDto> findByFilter(FindCommodityFilterDto findCommodityRequest) {
        return loadValidatedCommodityByFilterPort
                .loadByFilter(persistenceFindCommodityMapper.map(findCommodityFilterDtoMapper.map(findCommodityRequest)))
                .stream()
                .map(validatedCommodityDTOMapper::map)
                .toList();
    }
}
