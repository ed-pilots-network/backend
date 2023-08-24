package io.edpn.backend.trade.application.port.outgoing.system;

import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;

import java.util.List;

public interface LoadSystemsByFilterPort {


    List<System> loadByFilter(FindSystemFilter findSystemFilter);
}
