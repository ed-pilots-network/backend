package io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.service;

import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.dto.eddn.FssSignalDiscoveredMessage;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.dto.persistence.SystemEntity;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.usecase.ReceiveFssSignalDiscoveredMessageUseCase;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.domain.repository.SystemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ReceiveFssSignalDiscoveredMessageService implements ReceiveFssSignalDiscoveredMessageUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveFssSignalDiscoveredMessageService.class);

    private final SystemRepository systemRepository;

    @Override
    public void receive(FssSignalDiscoveredMessage.V1 message) {
        long start = System.nanoTime();
        LOGGER.debug("ReceiveFssSignalDiscoveredMessageService.receive -> fssSignalDiscoveredMessage: " + message);

        FssSignalDiscoveredMessage.V1.Message payload = message.getMessage();

        String systemName = payload.getStarSystem();
        long systemAddress = payload.getSystemAddress();
        Double[] coordinates = payload.getStarPos();

        //ignore individual signal

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

        if (changed) {
            systemRepository.update(system);
        }

        LOGGER.info("ReceiveFssSignalDiscoveredMessageService.receive -> the message has been processed");
        LOGGER.trace("ReceiveFssSignalDiscoveredMessageService.receive -> took " + (System.nanoTime() - start) + " nanosecond");
    }
}
