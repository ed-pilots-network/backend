package io.edpn.backend.exploration.application.port.outgoing.faction;

import io.edpn.backend.exploration.application.domain.Faction;

public interface SaveOrUpdateFactionPort {

    Faction saveOrUpdate(Faction faction);
}
