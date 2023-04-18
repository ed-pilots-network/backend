package io.eddb.eddb2backend.application.dto.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommodityEntity extends BaseEntity {

    private String name;
}



