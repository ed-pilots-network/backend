package io.edpn.backend.trade.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.domain.repository.CommodityMarketInfoRepository;
import io.edpn.backend.trade.domain.repository.CommodityRepository;
import io.edpn.backend.trade.domain.repository.LocateCommodityRepository;
import io.edpn.backend.trade.domain.repository.MarketDatumRepository;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.domain.repository.StationRepository;
import io.edpn.backend.trade.domain.repository.SystemRepository;
import io.edpn.backend.trade.domain.repository.ValidatedCommodityRepository;
import io.edpn.backend.trade.infrastructure.kafka.KafkaTopicHandler;
import io.edpn.backend.trade.infrastructure.kafka.sender.KafkaMessageSender;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.CommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.CommodityMarketInfoMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.LocateCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.StationMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.SystemMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.ValidatedCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.FindCommodityFilterMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.LocateCommodityFilterMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.LocateCommodityEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.RequestDataMessageEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.StationEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.ValidatedCommodityEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.repository.MybatisCommodityMarketInfoRepository;
import io.edpn.backend.trade.infrastructure.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.repository.MybatisMarkerDatumRepository;
import io.edpn.backend.trade.infrastructure.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.infrastructure.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.infrastructure.persistence.repository.MybatisValidatedCommodityRepository;
import io.edpn.backend.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration("TradeModuleRepositoryConfig")
public class RepositoryConfig {

    @Bean
    public CommodityRepository commodityRepository(IdGenerator idGenerator, CommodityMapper commodityMapper, CommodityEntityMapper commodityEntityMapper) {
        return new MybatisCommodityRepository(idGenerator, commodityMapper, commodityEntityMapper);
    }

    @Bean
    public CommodityMarketInfoRepository commodityMarketInfoRepository(CommodityMarketInfoMapper commodityMarketInfoMapper, CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper) {
        return new MybatisCommodityMarketInfoRepository(commodityMarketInfoMapper, commodityMarketInfoEntityMapper);
    }

    @Bean
    public StationRepository stationRepository(IdGenerator idGenerator, StationMapper stationMapper, StationEntityMapper stationEntityMapper, MarketDatumEntityMapper marketDatumEntityMapper) {
        return new MybatisStationRepository(idGenerator, stationMapper, stationEntityMapper, marketDatumEntityMapper);
    }

    @Bean
    public SystemRepository systemRepository(IdGenerator idGenerator, SystemMapper systemMapper, SystemEntityMapper systemEntityMapper) {
        return new MybatisSystemRepository(idGenerator, systemMapper, systemEntityMapper);
    }

    @Bean
    public RequestDataMessageRepository requestDataMessageRepository(ObjectMapper objectMapper, RequestDataMessageEntityMapper requestDataMessageEntityMapper, RequestDataMessageMapper requestDataMessageMapper, KafkaTopicHandler kafkaTopicHandler, KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate) {
        return new KafkaMessageSender(objectMapper, requestDataMessageEntityMapper, requestDataMessageMapper, kafkaTopicHandler, jsonNodekafkaTemplate);
    }

    @Bean
    public MarketDatumRepository marketDatumRepository(MarketDatumEntityMapper marketDatumEntityMapper) {
        return new MybatisMarkerDatumRepository(marketDatumEntityMapper);
    }

    @Bean
    public LocateCommodityRepository locateCommodityRepository(LocateCommodityMapper locateCommodityMapper, LocateCommodityEntityMapper locateCommodityEntityMapper, LocateCommodityFilterMapper locateCommodityFilterMapper) {
        return new MybatisLocateCommodityRepository(locateCommodityMapper, locateCommodityEntityMapper, locateCommodityFilterMapper);
    }
    
    @Bean
    public ValidatedCommodityRepository validatedCommodityRepository(ValidatedCommodityMapper validatedCommodityMapper, ValidatedCommodityEntityMapper validatedCommodityEntityMapper, FindCommodityFilterMapper findCommodityFilterMapper) {
        return new MybatisValidatedCommodityRepository(validatedCommodityMapper, validatedCommodityEntityMapper, findCommodityFilterMapper);
    }
}
