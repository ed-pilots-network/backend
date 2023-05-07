package io.edpn.backend.messageprocessor.application.dto.eddn;

import java.time.LocalDateTime;

public interface withMessageTimestamp {

    LocalDateTime getMessageTimeStamp();
}
