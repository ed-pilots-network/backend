package io.edpn.backend.exploration.application.dto.persistence.entity;

import io.edpn.backend.util.Module;

public interface StationMaxLandingPadSizeRequestEntity {

    String getSystemName();

    String getStation();

    Module getRequestingModule();

}
