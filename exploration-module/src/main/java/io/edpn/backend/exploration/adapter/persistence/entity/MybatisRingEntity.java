package io.edpn.backend.exploration.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MybatisRingEntity {
    private UUID id;
    private Long innerRadius;
    private Long mass;
    private String name;
    private Long outerRadius;
    private String ringClass;
    private UUID bodyId;
    private UUID starId;
}
