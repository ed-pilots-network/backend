package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.port.outgoing.commodity.CreateCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.commodity.LoadCommodityByIdPort;
import io.edpn.backend.trade.application.port.outgoing.commodity.LoadOrCreateCommodityByNamePort;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CommodityRepository implements CreateCommodityPort, LoadCommodityByIdPort, LoadOrCreateCommodityByNamePort {
    
    private final IdGenerator idGenerator;
    private final MybatisCommodityEntityMapper mybatisCommodityEntityMapper;
    private final MybatisCommodityRepository mybatisCommodityRepository;
    
    @Override
    public Commodity create(Commodity commodity) {
        var entity = mybatisCommodityEntityMapper.map(commodity);
        
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        mybatisCommodityRepository.insert(entity);
        return loadById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("commodity with id: " + commodity.getId() + " could not be found after create"));
    }
    
    @Override
    public Optional<Commodity> loadById(UUID id) {
        return mybatisCommodityRepository.findById(id)
                .map(mybatisCommodityEntityMapper::map);
    }
    
    @Override
    public Commodity loadOrCreate(String name) {
        return mybatisCommodityRepository.findByName(name)
                .map(mybatisCommodityEntityMapper::map)
                .orElseGet(() -> {
                    Commodity s = Commodity.builder()
                            .name(name)
                            .build();
                    return create(s);
                });
    }
}
