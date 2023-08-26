package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Coordinate;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Slf4j
public class ReceiveNavRouteService implements ReceiveKafkaMessageUseCase<NavRouteMessage.V1> {

    private final IdGenerator idGenerator;
    private final SaveSystemPort saveSystemPort;
    private final SendMessagePort sendMessagePort;
    private final LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final LoadSystemEliteIdRequestBySystemNamePort loadSystemEliteIdRequestBySystemNamePort;
    private final DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    private final SystemEliteIdResponseMapper systemEliteIdResponseMapper;
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final Executor executor;

    @Override
    public void receive(NavRouteMessage.V1 message) {
        long start = java.lang.System.nanoTime();
        log.debug("DefaultReceiveNavRouteMessageUseCase.receive -> CommodityMessage: {}", message);

        //LocalDateTime updateTimestamp = message.getMessageTimeStamp();
        NavRouteMessage.V1.Payload payload = message.message();
        NavRouteMessage.V1.Item[] routeItems = payload.items();


        CompletableFuture.allOf(List.of(routeItems).parallelStream()
                        .map(this::process)
                        .toArray(CompletableFuture[]::new))
                .join();

        log.trace("DefaultReceiveNavRouteMessageUseCase.receive -> took {} nanosecond", java.lang.System.nanoTime() - start);
        log.info("DefaultReceiveNavRouteMessageUseCase.receive -> the message has been processed");
    }

    private CompletableFuture<Void> process(NavRouteMessage.V1.Item item) {
        return createOrUpdateFromItem(item)
                .thenComposeAsync(system -> {
                    CompletableFuture<Void> sendCoordinateResponseFuture = CompletableFuture.runAsync(() -> sendCoordinateResponse(system), executor);
                    CompletableFuture<Void> sendEliteIdResponseFuture = CompletableFuture.runAsync(() -> sendEliteIdResponse(system), executor);

                    return CompletableFuture.allOf(sendCoordinateResponseFuture, sendEliteIdResponseFuture);
                });
    }

    private CompletableFuture<System> createOrUpdateFromItem(NavRouteMessage.V1.Item item) {
        return CompletableFuture.supplyAsync(() ->
                saveSystemPort.save(
                        new System(idGenerator.generateId(),
                                item.systemAddress(),
                                item.starSystem(),
                                item.starClass(),
                                new Coordinate(item.starPos()[0], item.starPos()[1], item.starPos()[2]))));
    }

    private void sendCoordinateResponse(System system) {
        loadSystemCoordinateRequestBySystemNamePort.loadByName(system.name()).parallelStream()
                .forEach(systemCoordinateRequest -> CompletableFuture.runAsync(() -> {
                    try {
                        SystemCoordinatesResponse systemCoordinatesResponse = systemCoordinatesResponseMapper.map(system);
                        String stringJson = objectMapper.writeValueAsString(systemCoordinatesResponse);
                        String topic = Topic.Response.SYSTEM_COORDINATES.getFormattedTopicName(systemCoordinateRequest.requestingModule());
                        Message message = new Message(topic, stringJson);
                        MessageDto messageDto = messageMapper.map(message);

                        boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(messageDto));
                        if (sendSuccessful) {
                            deleteSystemCoordinateRequestPort.delete(system.name(), systemCoordinateRequest.requestingModule());
                        }
                    } catch (JsonProcessingException jpe) {
                        throw new RuntimeException(jpe);
                    }
                }, executor));
    }

    private void sendEliteIdResponse(System system) {
        loadSystemEliteIdRequestBySystemNamePort.loadByName(system.name()).parallelStream()
                .forEach(systemEliteIdRequest -> CompletableFuture.runAsync(() -> {
                    try {
                        SystemEliteIdResponse systemEliteIdsResponse = systemEliteIdResponseMapper.map(system);
                        String stringJson = objectMapper.writeValueAsString(systemEliteIdsResponse);
                        String topic = Topic.Response.SYSTEM_ELITE_ID.getFormattedTopicName(systemEliteIdRequest.requestingModule());
                        Message message = new Message(topic, stringJson);
                        MessageDto messageDto = messageMapper.map(message);

                        boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(messageDto));
                        if (sendSuccessful) {
                            deleteSystemEliteIdRequestPort.delete(system.name(), systemEliteIdRequest.requestingModule());
                        }
                    } catch (JsonProcessingException jpe) {
                        throw new RuntimeException(jpe);
                    }
                }, executor));

    }
}
