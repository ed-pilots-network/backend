package io.eddb.eddb2backend.domain.model.station;

import io.eddb.eddb2backend.domain.model.body.Body;
import io.eddb.eddb2backend.domain.model.common.Allegiance;
import io.eddb.eddb2backend.domain.model.common.Economy;
import io.eddb.eddb2backend.domain.model.common.Faction;
import io.eddb.eddb2backend.domain.model.common.Government;
import io.eddb.eddb2backend.infrastructure.persistence.mybatis.entity.station.StationEntity;
import lombok.Builder;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
//TODO: Look for states, same issue as systems
//
public record Station(Long id, String name, LocalDateTime lastUpdated, LandingPad maxLandingPadSize,
                      Long distanceToStar, Government government, Allegiance allegiance, Type type,
                      boolean hasBlackMarket, boolean hasMarket, boolean hasRefuel, boolean hasRepair, boolean hasRearm,
                      boolean hasOutfitting, boolean hasShipyard, boolean hasDocking, boolean hasCommodities,
                      boolean hasMaterialTrader, boolean hasTechnologyBroker, boolean hasCarrierVendor,
                      boolean hasCarrierAdministration, boolean hasInterstellarFactors,
                      boolean hasUniversalCartographics, Collection<Commodity> importCommodities,
                      Collection<Commodity> exportCommodities, Collection<Commodity> prohibitedCommodities,
                      Collection<Economy> economies, LocalDateTime shipYardUpdatedAt, LocalDateTime outfittingUpdatedAt,
                      LocalDateTime marketUpdatedAt, boolean isPlanetary, Collection<Ship> sellingShips,
                      Collection<Module> modules, Body body, Faction controllingMinorFaction, Long edMarketId){
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        Station that = (Station) o;
        
        return new EqualsBuilder().append(name, that.name).isEquals();
    }
}
