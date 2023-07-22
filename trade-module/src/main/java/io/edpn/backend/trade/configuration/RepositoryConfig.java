package io.edpn.backend.trade.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.domain.repository.*;
import io.edpn.backend.trade.infrastructure.kafka.KafkaTopicHandler;
import io.edpn.backend.trade.infrastructure.kafka.sender.KafkaMessageSender;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.*;
import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.LocateCommodityFilterMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.*;
import io.edpn.backend.trade.infrastructure.persistence.repository.*;
import io.edpn.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("TradeModuleRepositoryConfig")
public class RepositoryConfig {

    @Bean(name = "tradeCommodityRepository")
    public CommodityRepository commodityRepository(@Qualifier("tradeIdGenerator") IdGenerator idGenerator, CommodityMapper commodityMapper, CommodityEntityMapper commodityEntityMapper) {
        return new MybatisCommodityRepository(idGenerator, commodityMapper, commodityEntityMapper);
    }

    @Bean(name = "tradeCommodityMarketInfoRepository")
    public CommodityMarketInfoRepository commodityMarketInfoRepository(CommodityMarketInfoMapper commodityMarketInfoMapper, CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper) {
        return new MybatisCommodityMarketInfoRepository(commodityMarketInfoMapper, commodityMarketInfoEntityMapper);
    }

    @Bean(name = "tradeStationRepository")
    public StationRepository stationRepository(@Qualifier("tradeIdGenerator") IdGenerator idGenerator, StationMapper stationMapper, StationEntityMapper stationEntityMapper, MarketDatumEntityMapper marketDatumEntityMapper) {
        return new MybatisStationRepository(idGenerator, stationMapper, stationEntityMapper, marketDatumEntityMapper);
    }

    @Bean(name = "tradeSystemRepository")
    public SystemRepository systemRepository(@Qualifier("tradeIdGenerator") IdGenerator idGenerator, SystemMapper systemMapper, SystemEntityMapper systemEntityMapper) {
        return new MybatisSystemRepository(idGenerator, systemMapper, systemEntityMapper);
    }

    @Bean(name = "tradeRequestDataMessageRepository")
    public RequestDataMessageRepository requestDataMessageRepository(ObjectMapper objectMapper, RequestDataMessageEntityMapper requestDataMessageEntityMapper, RequestDataMessageMapper requestDataMessageMapper, KafkaTopicHandler kafkaTopicHandler, @Qualifier("tradeJsonNodekafkaTemplate") KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate) {
        return new KafkaMessageSender(objectMapper, requestDataMessageEntityMapper, requestDataMessageMapper, kafkaTopicHandler, jsonNodekafkaTemplate);
    }

    @Bean(name = "tradeMarketDatumRepository")
    public MarketDatumRepository marketDatumRepository(MarketDatumEntityMapper marketDatumEntityMapper) {
        return new MybatisMarkerDatumRepository(marketDatumEntityMapper);
    }

    @Bean(name = "tradeLocateCommodityRepository")
    public LocateCommodityRepository locateCommodityRepository(LocateCommodityMapper locateCommodityMapper, LocateCommodityEntityMapper locateCommodityEntityMapper, LocateCommodityFilterMapper locateCommodityFilterMapper) {
        return new MybatisLocateCommodityRepository(locateCommodityMapper, locateCommodityEntityMapper, locateCommodityFilterMapper);
    }
}
