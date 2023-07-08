package io.edpn.backend.trade.domain.repository;

import io.edpn.backend.trade.domain.model.FindCommodity;
import io.edpn.backend.trade.domain.filter.FindCommodityFilter;

import java.util.List;

public interface FindCommodityRepository {
    List<FindCommodity> findCommodityByFilter(FindCommodityFilter findCommodityFilter);
}
