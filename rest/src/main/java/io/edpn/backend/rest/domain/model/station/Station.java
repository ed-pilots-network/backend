package io.edpn.backend.rest.domain.model.station;

import io.edpn.backend.rest.domain.model.body.Body;
import io.edpn.backend.rest.domain.model.common.Allegiance;
import io.edpn.backend.rest.domain.model.common.Economy;
import io.edpn.backend.rest.domain.model.common.Faction;
import io.edpn.backend.rest.domain.model.common.Government;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Builder;

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
                      Collection<Module> modules, Body body, Faction controllingMinorFaction, Long edMarketId) {

}
