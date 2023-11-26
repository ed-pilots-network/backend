package io.edpn.backend.exploration.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SystemCoordinatesUpdatedEvent extends ApplicationEvent {

    private final String systemName;

    public SystemCoordinatesUpdatedEvent(Object source, String systemName) {
        super(source);
        this.systemName = systemName;
    }
}
