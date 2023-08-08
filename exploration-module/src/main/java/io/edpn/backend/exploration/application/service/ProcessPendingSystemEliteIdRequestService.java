package io.edpn.backend.exploration.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.dto.mapper.MessageMapper;
import io.edpn.backend.exploration.application.dto.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.port.incomming.ProcessPendingDataRequestUseCase;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.util.Topic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@AllArgsConstructor
@Slf4j
public class ProcessPendingSystemEliteIdRequestService implements ProcessPendingDataRequestUseCase<SystemEliteIdRequest> {

    private final LoadAllSystemEliteIdRequestPort loadAllSystemEliteIdRequestPort;
    private final LoadSystemPort loadSystemPort;
    private final SendMessagePort sendMessagePort;
    private final DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort;
    private final SystemEliteIdResponseMapper systemEliteIdsResponseMapper;
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final RetryTemplate retryTemplate;
    private final Executor executor;

    @Override
    @Scheduled(cron = "0 0 */12 * * *")
    public void processPending() {
        loadAllSystemEliteIdRequestPort.loadAll().parallelStream()
                .forEach(systemEliteIdRequest -> CompletableFuture.runAsync(() -> loadSystemPort.load(systemEliteIdRequest.systemName())
                        .ifPresent(system -> {
                                    SystemEliteIdResponse systemEliteIdsResponse = systemEliteIdsResponseMapper.map(system);
                                    String stringJson = objectMapper.valueToTree(systemEliteIdsResponse).toString();
                                    String topic = Topic.Response.SYSTEM_ELITE_ID.getFormattedTopicName(systemEliteIdRequest.requestingModule());
                                    Message message = new Message(topic, stringJson);
                                    MessageDto messageDto = messageMapper.map(message);

                                    boolean sendSuccessful = retryTemplate.execute(retryContext -> sendMessagePort.send(messageDto));
                                    if (sendSuccessful) {
                                        deleteSystemEliteIdRequestPort.delete(systemEliteIdRequest.systemName(), systemEliteIdRequest.requestingModule());
                                    }
                                }
                        ), executor));
    }
}
