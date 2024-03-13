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
public class MybatisSystemEntity {
    private UUID id;
    private String name;
    private Long eliteId;
    private String primaryStarClass;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
}

