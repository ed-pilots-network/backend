package io.edpn.backend.exploration.adapter.persistence.entity;

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
public class SystemEntity implements io.edpn.backend.exploration.application.dto.SystemEntity {
    private UUID id;
    private String name;
    private Long eliteId;
    private String starClass;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
}

