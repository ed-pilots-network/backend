package io.edpn.backend.trade.application.dto.persistence.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindSystemFilter;

public interface PersistenceFindSystemFilterMapper {

    PersistenceFindSystemFilter map(FindSystemFilter findSystemFilter);

}
