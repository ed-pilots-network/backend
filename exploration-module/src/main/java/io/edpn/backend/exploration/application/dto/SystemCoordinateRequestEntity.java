package io.edpn.backend.exploration.application.dto;

import io.edpn.backend.util.Module;

public interface SystemCoordinateRequestEntity {

    String getSystemName();

    Module getRequestingModule();

}
