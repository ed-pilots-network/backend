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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    
    @ManyToMany(mappedBy = "importCommodities", fetch = FetchType.LAZY)
    private Collection<StationEntity> importingStationEntities;
    
    @ManyToMany(mappedBy = "exportCommodities", fetch = FetchType.LAZY)
    private Collection<StationEntity> exportingStationEntities;
    
    @ManyToMany(mappedBy = "prohibitedCommodities", fetch = FetchType.LAZY)
    private Collection<StationEntity> prohibitingStationEntities;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        CommodityEntity that = (CommodityEntity) o;
        
        return new EqualsBuilder().append(name, that.name).isEquals();
    }
    
    @Override
    public int hashCode() {
        return Optional.ofNullable(name)
                .map(name -> new HashCodeBuilder(17, 41)
                        .append(name)
                        .toHashCode())
                .orElse(0);
    }
    
    public static class Mapper {
        public static Optional<CommodityEntity> map(Commodity commodity) {
            return Optional.ofNullable(commodity)
                    .map(c -> CommodityEntity.builder()
                            .id(c.id())
                            .name(c.name())
                            .build());
        }
        
        public static Optional<Commodity> map(CommodityEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Commodity.builder()
                            .id(e.getId())
                            .name(e.getName())
                            .build());
        }
    }
    
}
