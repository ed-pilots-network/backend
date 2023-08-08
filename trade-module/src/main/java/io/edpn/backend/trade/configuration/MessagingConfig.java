package io.edpn.backend.trade.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import io.edpn.backend.trade.infrastructure.kafka.processor.CommodityV3MessageProcessor;
import io.edpn.backend.trade.infrastructure.kafka.processor.StationArrivalDistanceResponseMessageProcessor;
import io.edpn.backend.trade.infrastructure.kafka.processor.StationMaxLandingPadSizeResponseMessageProcessor;
import io.edpn.backend.trade.infrastructure.kafka.processor.SystemCoordinatesResponseMessageProcessor;
import io.edpn.backend.trade.infrastructure.kafka.processor.SystemEliteIdResponseMessageProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleMessagingConfig")
public class MessagingConfig {

    @Bean(name = "tradeCommodityV3MessageProcessor")
    public MessageProcessor<CommodityMessage.V3> commodityV3MessageProcessor(ReceiveCommodityMessageUseCase receiveCommodityMessageUsecase, ObjectMapper objectMapper) {
        return new CommodityV3MessageProcessor(receiveCommodityMessageUsecase, objectMapper);
    }

    @Bean(name = "tradeSystemCoordinatesResponseMessageProcessor")
    public MessageProcessor<SystemCoordinatesResponse> systemCoordinatesResponseMessageProcessor(ReceiveDataRequestResponseUseCase<SystemCoordinatesResponse> receiveSystemCoordinatesResponseUseCase, ObjectMapper objectMapper) {
        return new SystemCoordinatesResponseMessageProcessor(receiveSystemCoordinatesResponseUseCase, objectMapper);
    }

    @Bean(name = "tradeSystemEliteIdResponseMessageProcessor")
    public MessageProcessor<SystemEliteIdResponse> systemEliteIdResponseMessageProcessor(ReceiveDataRequestResponseUseCase<SystemEliteIdResponse> receiveSystemCoordinatesResponseUseCase, ObjectMapper objectMapper) {
        return new SystemEliteIdResponseMessageProcessor(receiveSystemCoordinatesResponseUseCase, objectMapper);
    }

    @Bean(name = "tradeStationArrivalDistanceResponseMessageProcessor")
    public MessageProcessor<StationArrivalDistanceResponse> stationArrivalDistanceResponseMessageProcessor(
            ReceiveDataRequestResponseUseCase<StationArrivalDistanceResponse> receiveDataRequestResponseUseCase,
            ObjectMapper objectMapper
    ) {
        return new StationArrivalDistanceResponseMessageProcessor(receiveDataRequestResponseUseCase, objectMapper);
    }

    @Bean(name = "tradeStationMaxLandingPadSizeResponseMessageProcessor")
    public MessageProcessor<StationMaxLandingPadSizeResponse> stationMaxLandingPadSizeResponseMessageProcessor(
            ReceiveDataRequestResponseUseCase<StationMaxLandingPadSizeResponse> receiveDataRequestResponseUseCase,
            ObjectMapper objectMapper
    ) {
        return new StationMaxLandingPadSizeResponseMessageProcessor(receiveDataRequestResponseUseCase, objectMapper);
    }
}
