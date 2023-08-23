package io.edpn.backend.trade.adapter.persistence.entity;

import io.edpn.backend.trade.application.dto.persistence.entity.CommodityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MybatisCommodityEntity implements CommodityEntity {
    
    private UUID id;
    private String name;
}
