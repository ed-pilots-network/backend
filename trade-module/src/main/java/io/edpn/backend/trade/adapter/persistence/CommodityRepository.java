package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.port.outgoing.commodity.CreateOrLoadCommodityPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommodityRepository implements CreateOrLoadCommodityPort {

    private final MybatisCommodityEntityMapper mybatisCommodityEntityMapper;
    private final MybatisCommodityRepository mybatisCommodityRepository;

    @Override
    public Commodity createOrLoad(Commodity commodity) {
        return mybatisCommodityEntityMapper.map(mybatisCommodityRepository.createOrUpdateOnConflict(mybatisCommodityEntityMapper.map(commodity)));
    }
}
