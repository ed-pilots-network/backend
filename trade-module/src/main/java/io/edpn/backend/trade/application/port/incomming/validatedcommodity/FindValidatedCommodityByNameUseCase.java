package io.edpn.backend.trade.application.port.incomming.validatedcommodity;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;

import java.util.Optional;

public interface FindValidatedCommodityByNameUseCase {

    Optional<ValidatedCommodity> findByName(String displayName);
}
