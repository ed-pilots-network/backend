package io.edpn.backend.exploration.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.persistence.SystemRepository;
import io.edpn.backend.exploration.adapter.web.dto.mapper.RestSystemDtoMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;
import io.edpn.backend.exploration.application.port.outgoing.body.SaveOrUpdateBodyPort;
import io.edpn.backend.exploration.application.port.outgoing.message.SendMessagePort;
import io.edpn.backend.exploration.application.port.outgoing.ring.SaveOrUpdateRingPort;
import io.edpn.backend.exploration.application.port.outgoing.star.SaveOrUpdateStarPort;
import io.edpn.backend.exploration.application.port.outgoing.system.LoadSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.system.SaveOrUpdateSystemPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.CreateIfNotExistsSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.DeleteSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadAllSystemCoordinateRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.LoadSystemCoordinateRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest.SystemCoordinatesResponseSender;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.CreateIfNotExistsSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.DeleteSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadAllSystemEliteIdRequestPort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.LoadSystemEliteIdRequestBySystemNamePort;
import io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest.SystemEliteIdResponseSender;
import io.edpn.backend.exploration.application.service.ReceiveJournalScanService;
import io.edpn.backend.exploration.application.service.ReceiveNavRouteService;
import io.edpn.backend.exploration.application.service.SystemControllerService;
import io.edpn.backend.exploration.application.service.SystemCoordinateInterModuleCommunicationService;
import io.edpn.backend.exploration.application.service.SystemCoordinatesResponseSenderService;
import io.edpn.backend.exploration.application.service.SystemEliteIdInterModuleCommunicationService;
import io.edpn.backend.exploration.application.service.SystemEliteIdResponseSenderService;
import io.edpn.backend.exploration.application.validation.LoadByNameContainingValidator;
import io.edpn.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.ExecutorService;

@Configuration("ExplorationServiceConfig")
public class ServiceConfig {

    @Bean(name = "explorationReceiveNavRouteService")
    public ReceiveNavRouteService receiveNavRouteService(
            @Qualifier("explorationIdGenerator") IdGenerator idGenerator,
            SaveOrUpdateSystemPort saveOrUpdateSystemPort,
            SystemCoordinatesResponseSender systemCoordinatesResponseSender,
            SystemEliteIdResponseSender systemEliteIdResponseSender,
            @Qualifier("virtualThreadPerTaskExecutor") ExecutorService executorService
    ) {
        return new ReceiveNavRouteService(
                idGenerator,
                saveOrUpdateSystemPort,
                systemCoordinatesResponseSender,
                systemEliteIdResponseSender,
                executorService);
    }

    @Bean(name = "explorationReceiveJournalScanService")
    public ReceiveJournalScanService receiveJournalScanService(
            @Qualifier("explorationIdGenerator") IdGenerator idGenerator,
            SaveOrUpdateBodyPort saveOrUpdateBodyPort,
            SaveOrUpdateRingPort saveOrUpdateRingPort,
            SaveOrUpdateStarPort saveOrUpdateStarPort,
            SaveOrUpdateSystemPort saveOrUpdateSystemPort
    ) {
        return new ReceiveJournalScanService(
                idGenerator,
                saveOrUpdateBodyPort,
                saveOrUpdateRingPort,
                saveOrUpdateStarPort,
                saveOrUpdateSystemPort);
    }

    @Bean(name = "explorationSystemCoordinateInterModuleCommunicationService")
    public SystemCoordinateInterModuleCommunicationService systemCoordinateInterModuleCommunicationService(
            LoadAllSystemCoordinateRequestPort loadAllSystemCoordinateRequestPort,
            CreateIfNotExistsSystemCoordinateRequestPort createIfNotExistsSystemCoordinateRequestPort,
            LoadSystemPort loadSystemPort,
            SystemCoordinatesResponseSender systemCoordinatesResponseSender,
            @Qualifier("virtualThreadPerTaskExecutor") ExecutorService executorService
    ) {
        return new SystemCoordinateInterModuleCommunicationService(
                loadAllSystemCoordinateRequestPort,
                createIfNotExistsSystemCoordinateRequestPort,
                loadSystemPort,
                systemCoordinatesResponseSender,
                executorService
        );
    }

    @Bean(name = "explorationSystemEliteIdInterModuleCommunicationService")
    public SystemEliteIdInterModuleCommunicationService systemEliteIdInterModuleCommunicationService(
            LoadAllSystemEliteIdRequestPort loadAllSystemEliteIdRequestPort,
            CreateIfNotExistsSystemEliteIdRequestPort createIfNotExistsSystemEliteIdRequestPort,
            LoadSystemPort loadSystemPort,
            SystemEliteIdResponseSender systemEliteIdResponseSender,
            @Qualifier("virtualThreadPerTaskExecutor") ExecutorService executorService
    ) {
        return new SystemEliteIdInterModuleCommunicationService(
                loadAllSystemEliteIdRequestPort,
                createIfNotExistsSystemEliteIdRequestPort,
                loadSystemPort,
                systemEliteIdResponseSender,
                executorService
        );
    }

    @Bean(name = "explorationLoadByNameContainingValidator")
    public LoadByNameContainingValidator loadByNameContainingValidator(
            @Value(value = "${exploration.loadbynamecontainingvalidator.min_length:3}") final int minLength,
            @Value(value = "${exploration.loadbynamecontainingvalidator.min_size:1}") final int minSize,
            @Value(value = "${exploration.loadbynamecontainingvalidator.max_size:100}") final int maxSize
    ) {
        return new LoadByNameContainingValidator(
                minLength,
                minSize,
                maxSize);
    }


    @Bean(name = "explorationSystemControllerService")
    public SystemControllerService systemControllerService(
            SystemRepository systemRepository,
            LoadByNameContainingValidator loadByNameContainingValidator,
            RestSystemDtoMapper restSystemDtoMapper) {
        return new SystemControllerService(
                systemRepository,
                loadByNameContainingValidator,
                restSystemDtoMapper);
    }

    @Bean("explorationSystemCoordinatesResponseSender")
    public SystemCoordinatesResponseSender systemCoordinatesResponseSender(
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
        return new SystemCoordinatesResponseSenderService(
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

    @Bean("explorationSystemEliteIdResponseSender")
    public SystemEliteIdResponseSender systemEliteIdResponseSender(
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
        return new SystemEliteIdResponseSenderService(
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
