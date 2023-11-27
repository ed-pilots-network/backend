package io.edpn.backend.exploration.application.service;

import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveOrUpdateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.SystemEliteIdResponseSender;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Slf4j
public class ReceiveNavRouteService implements ReceiveKafkaMessageUseCase<NavRouteMessage.V1> {

    private final IdGenerator idGenerator;
    private final SaveOrUpdateSystemPort saveOrUpdateSystemPort;
    private final SystemCoordinatesResponseSender systemCoordinatesResponseSender;
    private final SystemEliteIdResponseSender systemEliteIdResponseSender;
    private final ExecutorService executorService;

    @Override
    public void receive(NavRouteMessage.V1 message) {
        long start = java.lang.System.nanoTime();
        log.debug("DefaultReceiveNavRouteMessageUseCase.receive -> CommodityMessage: {}", message);

        NavRouteMessage.V1.Payload payload = message.message();
        NavRouteMessage.V1.Item[] routeItems = payload.items();

        Arrays.stream(routeItems).parallel()
                .forEach(item -> executorService.submit(() -> process(item)));

        log.trace("DefaultReceiveNavRouteMessageUseCase.receive -> took {} nanosecond", java.lang.System.nanoTime() - start);
        log.info("DefaultReceiveNavRouteMessageUseCase.receive -> the message has been processed");
    }

    private void process(NavRouteMessage.V1.Item item) {
        System system = createOrUpdateFromItem(item);

        executorService.submit(() -> systemCoordinatesResponseSender.sendResponsesForSystem(system.name()));
        executorService.submit(() -> systemEliteIdResponseSender.sendResponsesForSystem(system.name()));
    }

    private System createOrUpdateFromItem(NavRouteMessage.V1.Item item) {
        return saveOrUpdateSystemPort.saveOrUpdate(
                new System(idGenerator.generateId(),
                        item.systemAddress(),
                        item.starSystem(),
                        item.starClass(),
                        new Coordinate(item.starPos()[0], item.starPos()[1], item.starPos()[2])));
    }
}
