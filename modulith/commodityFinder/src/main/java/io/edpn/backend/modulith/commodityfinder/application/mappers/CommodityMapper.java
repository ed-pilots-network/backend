package io.edpn.backend.modulith.commodityfinder.application.mappers;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.CommodityEntity;
import io.edpn.backend.modulith.commodityfinder.domain.entity.Commodity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
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
