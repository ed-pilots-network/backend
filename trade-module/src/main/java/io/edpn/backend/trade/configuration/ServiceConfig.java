package io.edpn.backend.trade.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.application.service.DefaultSendStationDataRequestService;
import io.edpn.backend.trade.application.service.DefaultSendSystemDataRequestService;
import io.edpn.backend.trade.application.service.RequestStationArrivalDistanceService;
import io.edpn.backend.trade.application.service.RequestStationLandingPadSizeService;
import io.edpn.backend.trade.application.service.RequestSystemCoordinatesService;
import io.edpn.backend.trade.application.service.RequestSystemEliteIdService;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.domain.service.KafkaSenderService;
import io.edpn.backend.trade.domain.service.RequestDataService;
import io.edpn.backend.trade.domain.service.SendDataRequestService;
import io.edpn.backend.trade.infrastructure.kafka.KafkaTopicHandler;
import io.edpn.backend.trade.infrastructure.kafka.sender.RequestDataMessageKafkaSenderService;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("TradeModuleServiceConfig")
public class ServiceConfig {

    @Bean(name = "TradeModuleRequestStationArrivalDistanceService")
    public RequestDataService<Station> requestStationArrivalDistanceService(SendDataRequestService<StationDataRequest> stationDataRequestSendDataRequestService) {
        return new RequestStationArrivalDistanceService(stationDataRequestSendDataRequestService);
    }

    @Bean(name = "TradeModuleRequestStationLandingPadSizeService")
    public RequestDataService<Station> requestStationLandingPadSizeService(SendDataRequestService<StationDataRequest> stationDataRequestSendDataRequestService) {
        return new RequestStationLandingPadSizeService(stationDataRequestSendDataRequestService);
    }

    @Bean(name = "TradeModuleRequestSystemCoordinatesService")
    public RequestDataService<System> requestSystemCoordinatesService(SendDataRequestService<SystemDataRequest> systemDataRequestSendDataRequestService) {
        return new RequestSystemCoordinatesService(systemDataRequestSendDataRequestService);
    }

    @Bean(name = "TradeModuleRequestSystemEliteIdService")
    public RequestDataService<System> requestSystemEliteIdService(SendDataRequestService<SystemDataRequest> systemDataRequestSendDataRequestService) {
        return new RequestSystemEliteIdService(systemDataRequestSendDataRequestService);
    }

    @Bean(name = "tradeRequestDataMessageKafkaSenderService")
    public KafkaSenderService<RequestDataMessage> requestDataMessageKafkaSenderService(ObjectMapper objectMapper, RequestDataMessageRepository requestDataMessageRepository, RequestDataMessageMapper requestDataMessageMapper, KafkaTopicHandler kafkaTopicHandler, @Qualifier("tradeJsonNodekafkaTemplate") KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate) {
        return new RequestDataMessageKafkaSenderService(objectMapper, requestDataMessageRepository, kafkaTopicHandler, jsonNodekafkaTemplate);
    }

    @Bean(name = "tradeSystemDataRequestSendDataRequestService")
    public SendDataRequestService<SystemDataRequest> systemDataRequestSendDataRequestService(KafkaSenderService<RequestDataMessage> kafkaSenderService, ObjectMapper objectMapper) {
        return new DefaultSendSystemDataRequestService(kafkaSenderService, objectMapper);
    }

    @Bean(name = "tradeStationDataRequestSendDataRequestService")
    public SendDataRequestService<StationDataRequest> stationDataRequestSendDataRequestService(KafkaSenderService<RequestDataMessage> kafkaSenderService, ObjectMapper objectMapper) {
        return new DefaultSendStationDataRequestService(kafkaSenderService, objectMapper);
    }
}
