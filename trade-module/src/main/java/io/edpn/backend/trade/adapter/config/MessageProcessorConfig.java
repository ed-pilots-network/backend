package io.edpn.backend.trade.adapter.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationRequireOdysseyResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.trade.adapter.kafka.dto.mapper.KafkaMessageMapper;
import io.edpn.backend.trade.adapter.kafka.processor.CommodityV3MessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.StationArrivalDistanceResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.StationMaxLandingPadSizeResponseMessageProcessor;
import io.edpn.backend.trade.adapter.kafka.processor.StationRequireOdysseyResponseMessageProcessor;
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
public class MessageProcessorConfig {


    @Bean(name = "tradeCommodityV3MessageProcessor")
    public CommodityV3MessageProcessor commodityV3MessageProcessor(
            ReceiveKafkaMessageUseCase<CommodityMessage.V3> receiveCommodityMessageUseCase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new CommodityV3MessageProcessor(receiveCommodityMessageUseCase, objectMapper);
    }

    @Bean(name = "tradeStationArrivalDistanceResponseMessageProcessor")
    public StationArrivalDistanceResponseMessageProcessor stationArrivalDistanceResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<StationArrivalDistanceResponse> receiveCommodityMessageUseCase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new StationArrivalDistanceResponseMessageProcessor(receiveCommodityMessageUseCase, objectMapper);
    }

    @Bean(name = "tradeStationMaxLandingPadSizeResponseMessageProcessor")
    public StationMaxLandingPadSizeResponseMessageProcessor stationMaxLandingPadSizeResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<StationMaxLandingPadSizeResponse> receiveCommodityMessageUseCase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new StationMaxLandingPadSizeResponseMessageProcessor(receiveCommodityMessageUseCase, objectMapper);
    }

    @Bean(name = "tradeSystemCoordinatesResponseMessageProcessor")
    public SystemCoordinatesResponseMessageProcessor systemCoordinatesResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<SystemCoordinatesResponse> receiveCommodityMessageUseCase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new SystemCoordinatesResponseMessageProcessor(receiveCommodityMessageUseCase, objectMapper);
    }

    @Bean(name = "tradeSystemEliteIdResponseMessageProcessor")
    public SystemEliteIdResponseMessageProcessor systemEliteIdResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<SystemEliteIdResponse> receiveCommodityMessageUseCase,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper) {
        return new SystemEliteIdResponseMessageProcessor(receiveCommodityMessageUseCase, objectMapper);
    }

    @Bean(name = "tradeKafkaMessageSender")
    public KafkaMessageSender kafkaMessageSender(
            CreateTopicPort createTopicPort,
            @Qualifier("tradeMessageMapper") KafkaMessageMapper messageMapper,
            @Qualifier("tradeObjectMapper") ObjectMapper objectMapper,
            @Qualifier("tradeJsonNodekafkaTemplate") KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate
    ) {
        return new KafkaMessageSender(createTopicPort, messageMapper, objectMapper, jsonNodekafkaTemplate);
    }

    @Bean(name = "tradeStationRequireOdysseyResponseMessageProcessor")
    public StationRequireOdysseyResponseMessageProcessor stationRequireOdysseyResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<StationRequireOdysseyResponse> useCase,
            ObjectMapper objectMapper
    ) {
        return new StationRequireOdysseyResponseMessageProcessor(useCase, objectMapper);
    }

    @Bean(name = "tradeStationPlanetaryResponseMessageProcessor")
    public StationRequireOdysseyResponseMessageProcessor stationPlanetaryResponseMessageProcessor(
            ReceiveKafkaMessageUseCase<StationRequireOdysseyResponse> useCase,
            ObjectMapper objectMapper
    ) {
        return new StationRequireOdysseyResponseMessageProcessor(useCase, objectMapper);
    }
}
