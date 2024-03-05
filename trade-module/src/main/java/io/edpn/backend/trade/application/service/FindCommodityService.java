package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
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

    @Override
    public List<ValidatedCommodity> findAll() {
        return loadAllValidatedCommodityPort.loadAll();
    }

    @Override
    public Optional<ValidatedCommodity> findByName(String displayName) {
        return loadValidatedCommodityByNamePort.loadByName(displayName);
    }

    @Override
    public List<ValidatedCommodity> findByFilter(FindCommodityFilter findCommodityRequest) {
        return loadValidatedCommodityByFilterPort.loadByFilter(findCommodityRequest);
    }
}
