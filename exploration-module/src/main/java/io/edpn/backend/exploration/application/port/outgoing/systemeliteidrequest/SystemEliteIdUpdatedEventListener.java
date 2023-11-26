package io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.exploration.application.domain.SystemEliteIdUpdatedEvent;
import org.springframework.context.event.EventListener;

public interface SystemEliteIdUpdatedEventListener {

    @EventListener
    void onUpdatedEvent(SystemEliteIdUpdatedEvent event);
}