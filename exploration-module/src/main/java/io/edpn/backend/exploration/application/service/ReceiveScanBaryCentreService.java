package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.port.body.SaveOrUpdateBodyPort;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.ScanBaryCentreMessage;
import io.edpn.backend.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Slf4j
public class ReceiveScanBaryCentreService implements ReceiveKafkaMessageUseCase<ScanBaryCentreMessage.V1> {
    
    private final IdGenerator idGenerator;
    private final SaveOrUpdateBodyPort saveOrUpdateBodyPort;
    private final LoadSystemPort loadSystemPort;
    private final SendMessagePort sendMessagePort;
    private final LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort;
    private final DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort;
    private final SystemCoordinatesResponseMapper systemCoordinatesResponseMapper;
    private final LoadSystemEliteIdRequestBySystemNamePort loadSystemEliteIdRequestBySystemNamePort;
    private final DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    private final SystemEliteIdResponseMapper systemEliteIdResponseMapper;
    private final MessageDtoMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    
    @Override
    public void receive(ScanBaryCentreMessage.V1 message) {
        long start = java.lang.System.nanoTime();
        log.debug("DefaultReceiveNavRouteMessageUseCase.receive -> CommodityMessage: {}", message);
        
        //LocalDateTime updateTimestamp = message.getMessageTimeStamp();
        ScanBaryCentreMessage.V1.Payload payload = message.message();
        
        createOrUpdateFromPayload(payload);
        
        log.trace("DefaultReceiveNavRouteMessageUseCase.receive -> took {} nanosecond", java.lang.System.nanoTime() - start);
        log.info("DefaultReceiveNavRouteMessageUseCase.receive -> the message has been processed");
    }
    
    private void createOrUpdateFromPayload(ScanBaryCentreMessage.V1.Payload payload) {
        CompletableFuture.supplyAsync(() ->
                saveOrUpdateBodyPort.saveOrUpdate(
                        new Body(
                                idGenerator.generateId(),
                                null,
                                payload.ascendingNode(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                payload.bodyID(),
                                payload.eccentricity(),
                                null,
                                null,
                                payload.meanAnomaly(),
                                payload.orbitalInclination(),
                                payload.orbitalPeriod(),
                                null,
                                payload.periapsis(),
                                null,
                                null,
                                null,
                                null,
                                payload.semiMajorAxis(),
                                null,
                                null,
                                null,
                                loadSystemPort.load(payload.starSystem()).orElse(null),
                                null,
                                null,
                                null,
                                payload.horizons(),
                                payload.odyssey(),
                                null
                        )));
    }
}


