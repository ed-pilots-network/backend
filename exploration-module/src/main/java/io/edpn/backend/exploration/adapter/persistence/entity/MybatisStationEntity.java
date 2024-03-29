package io.edpn.backend.exploration.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MybatisStationEntity {
    private UUID id;
    private Long marketId;
    private String name;
    private String type;
    private Double distanceFromStar;
    private MybatisSystemEntity system;
    private Map<String, Integer> landingPads;
    private Map<String, Double> economies;
    private String economy;
    private List<String> services;
    private String government;
    private Boolean odyssey;
    private Boolean horizons;
    private LocalDateTime updatedAt;
}

