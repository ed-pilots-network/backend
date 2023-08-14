package io.edpn.backend.trade.adapter.web.dto.object;

import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.StationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

public record RestLocateCommodityDto(
        String validatedCommodityDisplayName,
        InnerStationDto station,
        String systemName,
        LocalDateTime pricesUpdatedAt,
        Long supply,
        Long demand,
        Long buyPrice,
        Long sellPrice,
        Double distance) implements LocateCommodityDto {
    
    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Jacksonized
    public static class InnerStationDto implements StationDto {
        String name;
        Double arrivalDistance;
        @Schema(example = "SMALL", allowableValues = "UNKNOWN, SMALL, MEDIUM, LARGE")
        String maxLandingPadSize;
        Boolean fleetCarrier;
        Boolean requireOdyssey;
        Boolean planetary;
        
        @Override
        public Long getMarketId() {
            return null;
        }
        
        @Override
        public String getSystemName() {
            return null;
        }
        
        @Override
        public LocalDateTime getMarketUpdatedAt() {
            return null;
        }
    }
}
