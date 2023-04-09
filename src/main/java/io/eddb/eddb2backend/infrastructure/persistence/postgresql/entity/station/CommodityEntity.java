package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;

import io.eddb.eddb2backend.domain.model.station.Commodity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Optional;

@Entity(name = "commodity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class CommodityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @ManyToMany(mappedBy = "importCommodities",fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Collection<StationEntity> importingStationEntities;
    
    @ManyToMany(mappedBy = "exportCommodities",fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Collection<StationEntity> exportingStationEntities;
    
    @ManyToMany(mappedBy = "prohibitedCommodities",fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Collection<StationEntity> prohibitingStationEntities;
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        CommodityEntity that = (CommodityEntity) o;
        
        return new EqualsBuilder().append(id, that.id).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(id)
                .map(id -> new HashCodeBuilder(17, 37)
                        .append(id)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static CommodityEntity map(Commodity commodity) {
            return CommodityEntity.builder()
                    .id(commodity.id())
                    .name(commodity.name())
                    .build();
        }
        
        public static Commodity map(CommodityEntity entity) {
            return Commodity.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }
    
}
