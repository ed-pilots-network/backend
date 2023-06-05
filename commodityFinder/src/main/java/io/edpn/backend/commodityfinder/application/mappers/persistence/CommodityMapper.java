package io.edpn.backend.commodityfinder.application.mappers.persistence;

import io.edpn.backend.commodityfinder.application.dto.persistence.CommodityEntity;
import io.edpn.backend.commodityfinder.domain.entity.Commodity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class CommodityMapper {

    public Commodity map(CommodityEntity commodityEntity) {
        return Commodity.builder()
                .id(commodityEntity.getId())
                .name(commodityEntity.getName())
                .build();
    }

    public CommodityEntity map(Commodity commodity) {
        return CommodityEntity.builder()
                .id(commodity.getId())
                .name(commodity.getName())
                .build();
    }
}
