package io.eddb.eddb2backend.domain.model.station;

import io.eddb.eddb2backend.domain.model.*;
import io.eddb.eddb2backend.domain.model.body.Body;
import io.eddb.eddb2backend.domain.model.Economy;
import io.eddb.eddb2backend.domain.model.system.System;
import lombok.Builder;

import java.lang.Module;
import java.time.LocalDateTime;
import java.util.Collection;

@Builder
//TODO: Look for states, same issue as systems
//
public record Station(Long id, String name, System system, LocalDateTime lastUpdated, LandingPad landingPad,
                      Long distanceToStar, Government government, Allegiance allegiance, Type type,
                      boolean hasBlackMarket, boolean hasMarket, boolean hasRefuel, boolean hasRepair, boolean hasRearm,
                      boolean hasOutfitting, boolean hasShipyard, boolean hasDocking, boolean hasCommodities,
                      boolean hasMaterialTrader, boolean hasTechnologyBroker, boolean hasCarrierVendor,
                      boolean hasCarrierAdministration, boolean hasInterstellarFactors,
                      boolean hasUniversalCartographics, Collection<Commodity> importCommodities,
                      Collection<Commodity> exportCommodities, Collection<Commodity> prohibitedCommodities,
                      Collection<Economy> economies, LocalDateTime shipYardUpdatedAt, LocalDateTime outfittingUpdatedAt,
                      LocalDateTime marketUpdateAt, boolean isPlanetary, Collection<Ship> sellingShips,
                      Collection<Module> modules, Body body, Faction controllingMinorFaction, Long edMarketId){
}
