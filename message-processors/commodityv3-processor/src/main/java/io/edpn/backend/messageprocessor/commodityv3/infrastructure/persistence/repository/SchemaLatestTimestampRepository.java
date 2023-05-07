package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.SchemaLatestTimestampEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.SchemaLatestTimestampEntityMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SchemaLatestTimestampRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.SchemaLatestTimestampRepository {

    private final SchemaLatestTimestampEntityMapper schemaLatestTimestampEntityMapper;

    @Override
    public Collection<SchemaLatestTimestampEntity> findAll() {
        return schemaLatestTimestampEntityMapper.findAll();
    }

    @Override
    public SchemaLatestTimestampEntity createOrUpdate(SchemaLatestTimestampEntity entity) {
        if (schemaLatestTimestampEntityMapper.findBySchema(entity.getSchema()).isPresent()) {
            schemaLatestTimestampEntityMapper.update(entity);
        } else {
            schemaLatestTimestampEntityMapper.insert(entity);
        }
        return schemaLatestTimestampEntityMapper.findBySchema(entity.getSchema())
                .orElseThrow(() -> new RuntimeException("SchemaLatestTimestamp with schema: " + entity.getSchema() + " could not be found after createOrUpdate"));
    }

    @Override
    public Optional<SchemaLatestTimestampEntity> findBySchema(String Schema) {
        return schemaLatestTimestampEntityMapper.findBySchema(Schema);
    }

    @Override
    public boolean isAfterLatest(String schema, LocalDateTime timestamp) {
        return schemaLatestTimestampEntityMapper.findBySchema(schema)
                .map(entity -> entity.getTimestamp().isBefore(timestamp))
                .orElse(true);
    }
}
