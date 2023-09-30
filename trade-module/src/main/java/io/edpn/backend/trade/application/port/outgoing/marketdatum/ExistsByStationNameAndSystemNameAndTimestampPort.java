package io.edpn.backend.trade.application.port.outgoing.marketdatum;

import java.time.LocalDateTime;

public interface ExistsByStationNameAndSystemNameAndTimestampPort {
    
    boolean exists(String systemName, String stationName, LocalDateTime timestamp);
}
