package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.applicationevent.SystemCoordinatesEventListener;
import io.edpn.backend.exploration.adapter.applicationevent.SystemEliteIdEventListener;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.ExecutorService;

@Configuration("ExplorationApplicationEventingConfig")
public class ApplicationEventingConfig {

    @Bean("explorationSystemCoordinatesUpdatedEventListener")
    public SystemCoordinatesEventListener SystemCoordinatesEventListener(
            LoadSystemPort loadSystemPort,
            LoadSystemCoordinateRequestBySystemNamePort loadSystemCoordinateRequestBySystemNamePort,
            DeleteSystemCoordinateRequestPort deleteSystemCoordinateRequestPort,
            SendMessagePort sendMessagePort,
            SystemCoordinatesResponseMapper systemCoordinatesResponseMapper,
            MessageDtoMapper messageMapper,
            ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("virtualThreadPerTaskExecutor") ExecutorService executorService
    ) {
        return new SystemCoordinatesEventListener(
                loadSystemPort,
                loadSystemCoordinateRequestBySystemNamePort,
                deleteSystemCoordinateRequestPort,
                sendMessagePort,
                systemCoordinatesResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executorService
        );
    }

    @Bean("explorationSystemCoordinatesUpdatedEventListener")
    public SystemEliteIdEventListener SystemEliteIdEventListener(
            LoadSystemPort loadSystemPort,
            LoadSystemEliteIdRequestBySystemNamePort loadSystemEliteIdRequestBySystemNamePort,
            DeleteSystemEliteIdRequestPort deleteSystemEliteIdRequestPort,
            SendMessagePort sendMessagePort,
            SystemEliteIdResponseMapper systemEliteIdResponseMapper,
            MessageDtoMapper messageMapper,
            ObjectMapper objectMapper,
            @Qualifier("explorationRetryTemplate") RetryTemplate retryTemplate,
            @Qualifier("virtualThreadPerTaskExecutor") ExecutorService executorService
    ) {
        return new SystemEliteIdEventListener(
                loadSystemPort,
                loadSystemEliteIdRequestBySystemNamePort,
                deleteSystemEliteIdRequestPort,
                sendMessagePort,
                systemEliteIdResponseMapper,
                messageMapper,
                objectMapper,
                retryTemplate,
                executorService
        );
    }
}
