package io.edpn.backend.messageprocessor.navroutev1.application.service;

import io.edpn.backend.messageprocessor.navroutev1.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessor.navroutev1.application.dto.persistence.SystemEntity;
import io.edpn.backend.messageprocessor.navroutev1.application.usecase.ReceiveNavRouteMessageUseCase;
import io.edpn.backend.messageprocessor.navroutev1.domain.repository.SystemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
public class ReceiveNavRouteMessageService implements ReceiveNavRouteMessageUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveNavRouteMessageService.class);

    private final SystemRepository systemRepository;

    @Override
    public void receive(NavRouteMessage.V1 message) {
        long start = System.nanoTime();
        LOGGER.debug("ReceiveNavRouteMessageService.receive -> NavRouteMessage: " + message);

        NavRouteMessage.V1.Message payload = message.getMessage();

        Arrays.stream(payload.getItems()).forEach(item -> {
            String systemName = item.getStarSystem();
            String starClass = item.getStarClass();
            long systemAddress = item.getSystemAddress();
            Double[] coordinates = item.getStarPos();

            // find system, if not found create
            SystemEntity system = systemRepository.findOrCreateByName(systemName);
            boolean changed = false;

            if (Objects.isNull(system.getEliteId())) {
                system.setEliteId(systemAddress);
                changed = true;
            }

            if (Objects.isNull(system.getXCoordinate())) {
                system.setXCoordinate(coordinates[0]);
                system.setYCoordinate(coordinates[1]);
                system.setZCoordinate(coordinates[2]);
                changed = true;
            }

            if(StringUtils.isBlank(system.getStarClass())) {
                system.setStarClass(starClass);
            }

            if (changed) {
                systemRepository.update(system);
            }
        });

        LOGGER.info("ReceiveNavRouteMessageService.receive -> the message has been processed");
        LOGGER.trace("ReceiveNavRouteMessageService.receive -> took " + (System.nanoTime() - start) + " nanosecond");
    }
}
