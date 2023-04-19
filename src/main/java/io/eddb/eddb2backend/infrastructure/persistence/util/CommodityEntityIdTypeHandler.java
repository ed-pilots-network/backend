package io.eddb.eddb2backend.infrastructure.persistence.util;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(CommodityEntity.class)
public class CommodityEntityIdTypeHandler extends AbstractEntityIdTypeHandler<CommodityEntity.Id> {
    public CommodityEntityIdTypeHandler() {
        super(CommodityEntity.Id.class);
    }
}
