package io.edpn.backend.exploration.adapter.persistence.entity;

import io.edpn.backend.util.Module;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MybatisStationMaxLandingPadSizeRequestEntity {
    private String systemName;
    private String stationName;
    private Module requestingModule;
}
