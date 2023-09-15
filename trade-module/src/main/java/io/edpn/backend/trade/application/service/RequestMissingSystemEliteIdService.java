package io.edpn.backend.trade.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.domain.filter.FindSystemFilter;
import io.edpn.backend.trade.application.dto.web.object.mapper.MessageMapper;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import io.edpn.backend.trade.application.port.outgoing.system.LoadSystemsByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.CreateSystemEliteIdRequestPort;
import io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest.RequestMissingSystemEliteIdUseCase;
import io.edpn.backend.util.Module;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@AllArgsConstructor
@Slf4j
public class RequestMissingSystemEliteIdService implements RequestMissingSystemEliteIdUseCase {

    public static final FindSystemFilter FIND_SYSTEM_FILTER = FindSystemFilter.builder()
            .hasEliteId(false)
            .build();

    private final LoadSystemsByFilterPort loadSystemsByFilterPort;
    private final CreateSystemEliteIdRequestPort createSystemEliteIdRequestPort;
    private final SendKafkaMessagePort sendKafkaMessagePort;
    private final RetryTemplate retryTemplate;
    private final Executor executor;
    private final ObjectMapper objectMapper;
    private final MessageMapper messageMapper;

    @Override
    @Scheduled(cron = "0 */12 * * *")
    public void requestMissing() {
        loadSystemsByFilterPort.loadByFilter(FIND_SYSTEM_FILTER).parallelStream()
                .forEach(system ->
                        CompletableFuture.runAsync(() -> {
                            SystemDataRequest systemDataRequest = new SystemDataRequest(Module.TRADE, system.getName());

                            JsonNode jsonNode = objectMapper.valueToTree(systemDataRequest);

                            Message message = Message.builder()
                                    .topic("systemEliteIdRequest")
                                    .message(jsonNode.toString())
                                    .build();

                            boolean sendSuccessful = retryTemplate.execute(retryContext ->
                                    sendKafkaMessagePort.send(messageMapper.map(message)));
                            if (sendSuccessful) {
                                createSystemEliteIdRequestPort.create(system.getName());
                            }
                        }, executor));
    }
}
