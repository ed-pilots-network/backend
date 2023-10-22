package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.dto.persistence.entity.RingEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.StarEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class MybatisRingEntity implements RingEntity {
    private UUID id;
    private Long innerRadius;
    private Long mass;
    private String name;
    private Long outerRadius;
    private String ringClass;
    
    private MybatisBodyEntity bodyEntity;
    private MybatisStarEntity starEntity;
}
