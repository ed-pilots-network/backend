package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;
import io.eddb.eddb2backend.domain.model.station.Station;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.body.BodyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.AllegianceEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.EconomyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.system.FactionEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity(name = "station")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    private Long id;
    
    @ToString.Include
    private String name;
    private LocalDateTime lastUpdated;
    private Long distanceToStar;
    private boolean hasBlackMarket;
    private boolean hasMarket;
    private boolean hasRefuel;
    private boolean hasRepair;
    private boolean hasRearm;
    private boolean hasOutfitting;
    private boolean hasShipyard;
    private boolean hasDocking;
    private boolean hasCommodities;
    private boolean hasMaterialTrader;
    private boolean hasTechnologyBroker;
    private boolean hasCarrierVendor;
    private boolean hasCarrierAdministration;
    private boolean hasInterstellarFactors;
    private boolean hasUniversalCartographics;
    private boolean isPlanetary;
    private LocalDateTime shipYardUpdatedAt;
    private LocalDateTime outfittingUpdatedAt;
    private LocalDateTime marketUpdatedAt;
    private Long edMarketId;
    
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_id")
    private Collection<CommodityEntity> importCommodities;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_id")
    private Collection<CommodityEntity> exportCommodities;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_id")
    private Collection<CommodityEntity> prohibitedCommodities;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landing_pad_id")
    private LandingPadEntity maxSizeLandingPadEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allegiance_id")
    private AllegianceEntity allegianceEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private TypeEntity typeEntity;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "economy_id")
    private Collection<EconomyEntity> economyEntities;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id")
    private Collection<ShipEntity> sellingShipEntities;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Collection<ModuleEntity> moduleEntities;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id")
    private BodyEntity bodyEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faction_id")
    private FactionEntity controllingMinorFactionEntity;
    
    
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        StationEntity that = (StationEntity) o;
        
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
        public static StationEntity map(Station station) {
            return StationEntity.builder()
                    .id(station.id())
                    .name(station.name())
                    .lastUpdated(station.lastUpdated())
                    .distanceToStar(station.distanceToStar())
                    .hasBlackMarket(station.hasBlackMarket())
                    .hasMarket(station.hasMarket())
                    .hasRefuel(station.hasRefuel())
                    .hasRepair(station.hasRepair())
                    .hasRearm(station.hasRearm())
                    .hasOutfitting(station.hasOutfitting())
                    .hasShipyard(station.hasShipyard())
                    .hasDocking(station.hasDocking())
                    .hasCommodities(station.hasCommodities())
                    .hasMaterialTrader(station.hasMaterialTrader())
                    .hasTechnologyBroker(station.hasTechnologyBroker())
                    .hasCarrierVendor(station.hasCarrierVendor())
                    .hasCarrierAdministration(station.hasCarrierAdministration())
                    .hasUniversalCartographics(station.hasUniversalCartographics())
                    .isPlanetary(station.isPlanetary())
                    .shipYardUpdatedAt(station.shipYardUpdatedAt())
                    .outfittingUpdatedAt(station.outfittingUpdatedAt())
                    .marketUpdatedAt(station.marketUpdatedAt())
                    .edMarketId(station.edMarketId())
                    .importCommodities(station.importCommodities().stream().map(CommodityEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    
                    .exportCommodities(station.exportCommodities().stream().map(CommodityEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    
                    .prohibitedCommodities(station.prohibitedCommodities().stream().map(CommodityEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    .maxSizeLandingPadEntity(LandingPadEntity.Mapper.map(station.maxLandingPadSize()))
                    .allegianceEntity(AllegianceEntity.Mapper.map(station.allegiance()))
                    .typeEntity(TypeEntity.Mapper.map(station.type()))
                    .economyEntities(station.economies().stream().map(EconomyEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    .sellingShipEntities(station.sellingShips()
                            .stream()
                            .map(ShipEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    .moduleEntities(station.modules().stream().map(ModuleEntity.Mapper::map).collect(Collectors.toSet()))
                    .bodyEntity(BodyEntity.Mapper.map(station.body()))
                    .controllingMinorFactionEntity(FactionEntity.Mapper.map(station.controllingMinorFaction()))
                    .build();
        }

        public static Station map(StationEntity entity) {
            return Station.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .lastUpdated(entity.getLastUpdated())
                    .distanceToStar(entity.getDistanceToStar())
                    .hasBlackMarket(entity.isHasBlackMarket())
                    .hasMarket(entity.isHasMarket())
                    .hasRefuel(entity.isHasRefuel())
                    .hasRepair(entity.isHasRepair())
                    .hasRearm(entity.isHasRearm())
                    .hasOutfitting(entity.isHasOutfitting())
                    .hasShipyard(entity.isHasShipyard())
                    .hasDocking(entity.isHasDocking())
                    .hasCommodities(entity.isHasCommodities())
                    .hasMaterialTrader(entity.isHasMaterialTrader())
                    .hasTechnologyBroker(entity.isHasTechnologyBroker())
                    .hasCarrierVendor(entity.isHasCarrierVendor())
                    .hasCarrierAdministration(entity.isHasCarrierAdministration())
                    .hasUniversalCartographics(entity.isHasUniversalCartographics())
                    .isPlanetary(entity.isPlanetary())
                    .shipYardUpdatedAt(entity.getShipYardUpdatedAt())
                    .outfittingUpdatedAt(entity.getOutfittingUpdatedAt())
                    .marketUpdatedAt(entity.getMarketUpdatedAt())
                    .edMarketId(entity.getEdMarketId())
                    .importCommodities(entity.getImportCommodities().stream().map(CommodityEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    
                    .exportCommodities(entity.getExportCommodities().stream().map(CommodityEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    
                    .prohibitedCommodities(entity.getProhibitedCommodities().stream().map(CommodityEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    .maxLandingPadSize(LandingPadEntity.Mapper.map(entity.getMaxSizeLandingPadEntity()))
                    .allegiance(AllegianceEntity.Mapper.map(entity.getAllegianceEntity()))
                    .type(TypeEntity.Mapper.map(entity.getTypeEntity()))
                    .economies(entity.getEconomyEntities().stream().map(EconomyEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    .sellingShips(entity.getSellingShipEntities()
                            .stream()
                            .map(ShipEntity.Mapper::map)
                            .collect(Collectors.toSet()))
                    .modules(entity.getModuleEntities().stream().map(ModuleEntity.Mapper::map).collect(Collectors.toSet()))
                    .body(BodyEntity.Mapper.map(entity.getBodyEntity()))
                    .controllingMinorFaction(FactionEntity.Mapper.map(entity.getControllingMinorFactionEntity()))
                    .build();
        }
    }
}
