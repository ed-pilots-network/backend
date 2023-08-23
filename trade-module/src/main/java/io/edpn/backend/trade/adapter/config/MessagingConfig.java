package io.edpn.backend.trade.adapter.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.CommodityV3MessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.StationArrivalDistanceResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.StationMaxLandingPadSizeResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.SystemCoordinatesResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.SystemEliteIdResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.sender.KafkaMessageSender;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import io.edpn.backend.trade.application.port.outgoing.kafka.CreateTopicPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("TradeModuleMessagingConfig")
public class MessagingConfig {
    
    @Bean(name = "tradeCommodityV3MessageProcessor")
    public CommodityV3MessageProcessor commodityV3MessageProcessor(
            ReceiveKafkaMessageUseCase<CommodityMessage.V3> receiveCommodityMessageUsecase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new CommodityV3MessageProcessor(receiveCommodityMessageUsecase, objectMapper);
    }
    
    @Bean(name = "tradeStationArrivalDistanceResponseMessageProcessor")
    public MessageProcessor<StationArrivalDistanceResponse> stationArrivalDistanceResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<StationArrivalDistanceResponse> receiveCommodityMessageUsecase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new StationArrivalDistanceResponseMessageProcessor(receiveCommodityMessageUsecase, objectMapper);
    }
    
    @Bean(name = "tradeStationMaxLandingPadSizeResponseMessageProcessor")
    public MessageProcessor<StationMaxLandingPadSizeResponse> stationMaxLandingPadSizeResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<StationMaxLandingPadSizeResponse> receiveCommodityMessageUsecase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new StationMaxLandingPadSizeResponseMessageProcessor(receiveCommodityMessageUsecase, objectMapper);
    }
    
    @Bean(name = "tradeSystemCoordinatesResponseMessageProcessor")
    public MessageProcessor<SystemCoordinatesResponse> systemCoordinatesResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<SystemCoordinatesResponse> receiveCommodityMessageUsecase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new SystemCoordinatesResponseMessageProcessor(receiveCommodityMessageUsecase, objectMapper);
    }
    
    @Bean(name = "tradeSystemEliteIdResponseMessageProcessor")
    public MessageProcessor<SystemEliteIdResponse> systemEliteIdResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<SystemEliteIdResponse> receiveCommodityMessageUsecase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new SystemEliteIdResponseMessageProcessor(receiveCommodityMessageUsecase, objectMapper);
    }
    
    @Bean(name = "tradeKafkaMessageSender")
    public KafkaMessageSender kafkaMessageSender(
            CreateTopicPort createTopicPort,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper,
            @Qualifier("tradeJsonNodekafkaTemplate") KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate
    ) {
        return new KafkaMessageSender(createTopicPort, objectMapper, jsonNodekafkaTemplate);
    }
}
