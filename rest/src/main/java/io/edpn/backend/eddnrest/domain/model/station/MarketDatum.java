package io.edpn.backend.eddnrest.domain.model.station;

import lombok.Builder;

import java.util.List;

@Builder
public record MarketDatum(int meanPrice, int buyPrice, int stock, int stockBracket, int sellPrice, int demand,
                          int demandBracket, List<String> statusFlags) {
}
