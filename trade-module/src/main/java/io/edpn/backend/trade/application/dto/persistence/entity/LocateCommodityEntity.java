package io.edpn.backend.trade.application.dto.persistence.entity;

import java.time.LocalDateTime;

public interface LocateCommodityEntity extends PersistencePageInfo {

    ValidatedCommodityEntity getValidatedCommodity();

    StationEntity getStation();

    LocalDateTime getPriceUpdatedAt();

    Long getSupply();

    Long getDemand();

    Long getBuyPrice();

    Long getSellPrice();

    Double getDistance();

}
