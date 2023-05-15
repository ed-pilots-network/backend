package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.EconomyEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.EconomyEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisEconomyRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.EconomyRepository {

    private final IdGenerator idGenerator;
    private final EconomyEntityMapper economyEntityMapper;

    @Override
    public EconomyEntity findOrCreateByName(String name) throws DatabaseEntityNotFoundException {
        return economyEntityMapper.findByName(name)
                .orElseGet(() -> {
                    var s = EconomyEntity.builder()
                            .name(name)
                            .build();
                    return create(s);
                });
    }

    @Override
    public EconomyEntity create(EconomyEntity entity) throws DatabaseEntityNotFoundException {
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        economyEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("economy with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<EconomyEntity> findById(UUID id) {
        return economyEntityMapper.findById(id);
    }
}
