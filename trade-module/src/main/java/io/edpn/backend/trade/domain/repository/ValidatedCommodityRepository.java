package io.edpn.backend.trade.domain.repository;

import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ValidatedCommodityRepository {
    
    Optional<ValidatedCommodity> findById(UUID id);
    
    List<ValidatedCommodity> findAll();
    
    List<ValidatedCommodity> findByFilter(FindCommodityFilter findCommodityFilter);
}
