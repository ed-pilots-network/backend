package io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.station;
import io.eddb.eddb2backend.domain.model.station.Station;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.body.BodyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.AllegianceEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.EconomyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.postgresql.entity.common.FactionEntity;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
        public static Optional<StationEntity> map(Station station) {
            return Optional.ofNullable(station)
                    .map(s ->
                    StationEntity.builder()
                    .id(s.id())
                    .name(s.name())
                    .lastUpdated(s.lastUpdated())
                    .distanceToStar(s.distanceToStar())
                    .hasBlackMarket(s.hasBlackMarket())
                    .hasMarket(s.hasMarket())
                    .hasRefuel(s.hasRefuel())
                    .hasRepair(s.hasRepair())
                    .hasRearm(s.hasRearm())
                    .hasOutfitting(s.hasOutfitting())
                    .hasShipyard(s.hasShipyard())
                    .hasDocking(s.hasDocking())
                    .hasCommodities(s.hasCommodities())
                    .hasMaterialTrader(s.hasMaterialTrader())
                    .hasTechnologyBroker(s.hasTechnologyBroker())
                    .hasCarrierVendor(s.hasCarrierVendor())
                    .hasCarrierAdministration(s.hasCarrierAdministration())
                    .hasUniversalCartographics(s.hasUniversalCartographics())
                    .isPlanetary(s.isPlanetary())
                    .shipYardUpdatedAt(s.shipYardUpdatedAt())
                    .outfittingUpdatedAt(s.outfittingUpdatedAt())
                    .marketUpdatedAt(s.marketUpdatedAt())
                    .edMarketId(s.edMarketId())
                    .importCommodities(
                            Optional.ofNullable(s.importCommodities())
                                    .map(i -> i.stream()
                                            .map(CommodityEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .exportCommodities(
                            Optional.ofNullable(s.exportCommodities())
                                    .map(e -> e.stream()
                                            .map(CommodityEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .prohibitedCommodities(
                            Optional.ofNullable(s.prohibitedCommodities())
                                    .map(p -> p.stream()
                                            .map(CommodityEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .maxSizeLandingPadEntity(LandingPadEntity.Mapper.map(s.maxLandingPadSize()).orElse(null))
                    .allegianceEntity(AllegianceEntity.Mapper.map(s.allegiance()).orElse(null))
                    .typeEntity(TypeEntity.Mapper.map(s.type()).orElse(null))
                    .economyEntities(
                            Optional.ofNullable(s.economies())
                                    .map(e -> e.stream()
                                            .map(EconomyEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .sellingShipEntities(
                            Optional.ofNullable(s.sellingShips())
                                    .map(ships -> ships.stream()
                                            .map(ShipEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .moduleEntities(
                            Optional.ofNullable(s.modules())
                                    .map(m -> m.stream()
                                            .map(ModuleEntity.Mapper::map)
                                            .map(entity -> entity.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .bodyEntity(BodyEntity.Mapper.map(s.body()).orElse(null))
                    .controllingMinorFactionEntity(FactionEntity.Mapper.map(s.controllingMinorFaction()).orElse(null))
                    .build());
        }

        public static Optional<Station> map(StationEntity entity) {
            return Optional.ofNullable(entity)
                    .map(e -> Station.builder()
                    .id(e.getId())
                    .name(e.getName())
                    .lastUpdated(e.getLastUpdated())
                    .distanceToStar(e.getDistanceToStar())
                    .hasBlackMarket(e.isHasBlackMarket())
                    .hasMarket(e.isHasMarket())
                    .hasRefuel(e.isHasRefuel())
                    .hasRepair(e.isHasRepair())
                    .hasRearm(e.isHasRearm())
                    .hasOutfitting(e.isHasOutfitting())
                    .hasShipyard(e.isHasShipyard())
                    .hasDocking(e.isHasDocking())
                    .hasCommodities(e.isHasCommodities())
                    .hasMaterialTrader(e.isHasMaterialTrader())
                    .hasTechnologyBroker(e.isHasTechnologyBroker())
                    .hasCarrierVendor(e.isHasCarrierVendor())
                    .hasCarrierAdministration(e.isHasCarrierAdministration())
                    .hasUniversalCartographics(e.isHasUniversalCartographics())
                    .isPlanetary(e.isPlanetary())
                    .shipYardUpdatedAt(e.getShipYardUpdatedAt())
                    .outfittingUpdatedAt(e.getOutfittingUpdatedAt())
                    .marketUpdatedAt(e.getMarketUpdatedAt())
                    .edMarketId(e.getEdMarketId())
                    .importCommodities(
                            Optional.ofNullable(e.getImportCommodities())
                                    .map(i -> i.stream()
                                            .map(CommodityEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .exportCommodities(
                            Optional.ofNullable(e.getExportCommodities())
                                    .map(exports -> exports.stream()
                                            .map(CommodityEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .prohibitedCommodities(
                            Optional.ofNullable(e.getProhibitedCommodities())
                                    .map(p -> p.stream()
                                            .map(CommodityEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .maxLandingPadSize(LandingPadEntity.Mapper.map(e.getMaxSizeLandingPadEntity()).orElse(null))
                    .allegiance(AllegianceEntity.Mapper.map(e.getAllegianceEntity()).orElse(null))
                    .type(TypeEntity.Mapper.map(e.getTypeEntity()).orElse(null))
                    .economies(
                            Optional.ofNullable(e.getEconomyEntities())
                                    .map(economies -> economies.stream()
                                            .map(EconomyEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .sellingShips(
                            Optional.ofNullable(e.getSellingShipEntities())
                                    .map(ships -> ships.stream()
                                            .map(ShipEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .modules(
                            Optional.ofNullable(e.getModuleEntities())
                                    .map(m -> m.stream()
                                            .map(ModuleEntity.Mapper::map)
                                            .map(element -> element.orElse(null))
                                            .collect(Collectors.toSet()))
                                    .orElse(null))
                    .body(BodyEntity.Mapper.map(e.getBodyEntity()).orElse(null))
                    .controllingMinorFaction(FactionEntity.Mapper.map(e.getControllingMinorFactionEntity()).orElse(null))
                    .build());
        }
    }
}
