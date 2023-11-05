package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.exploration.application.dto.persistence.entity.SystemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MybatisSystemEntity implements SystemEntity {
    private UUID id;
    private String name;
    private Long eliteId;
    private String primaryStarClass;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
}

