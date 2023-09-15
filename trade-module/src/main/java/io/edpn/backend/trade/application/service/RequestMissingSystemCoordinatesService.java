package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.CreateSystemCoordinateRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest.RequestMissingSystemCoordinatesUseCase;
import io.edpn.backend.util.Module;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@AllArgsConstructor
@Slf4j
public class RequestMissingSystemCoordinatesService implements RequestMissingSystemCoordinatesUseCase {

    public static final FindSystemFilter FIND_SYSTEM_FILTER = FindSystemFilter.builder()
            .hasCoordinates(false)
            .build();

    private final LoadSystemsByFilterPort loadSystemsByFilterPort;
    private final CreateSystemCoordinateRequestPort createSystemCoordinateRequestPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    @Scheduled(cron = "0 */12 * * *")
    public void requestMissing() {
        FindSystemFilter filter = FindSystemFilter.builder()
                .hasCoordinates(false)
                .hasEliteId(null)
                .name(null)
                .build();

        loadSystemsByFilterPort.loadByFilter(filter).parallelStream()
                .forEach(system ->
                        CompletableFuture.runAsync(() -> {
                            SystemDataRequest stationDataRequest = new SystemDataRequest(Module.TRADE, system.getName());

                            JsonNode jsonNode = objectMapper.valueToTree(stationDataRequest);

                            Message message = Message.builder()
                                    .topic("systemCoordinatesRequest")
                                    .message(jsonNode.toString())
                                    .build();

                            boolean sendSuccessful = retryTemplate.execute(retryContext ->
                                    sendKafkaMessagePort.send(messageMapper.map(message)));
                            if (sendSuccessful) {
                                createSystemCoordinateRequestPort.create(system.getName());
                            }
                        }, executor));
    }
}
