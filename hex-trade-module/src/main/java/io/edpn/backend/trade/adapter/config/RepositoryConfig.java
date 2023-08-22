package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.persistence.CommodityMarketInfoRepository;
import io.edpn.backend.trade.adapter.persistence.CommodityRepository;
import io.edpn.backend.trade.adapter.persistence.LocateCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.MarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.ValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisLocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisSystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisValidatedCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityMarketInfoRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisValidatedCommodityRepository;
import io.edpn.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeRepositoryConfig")
public class RepositoryConfig {

    @Bean(name = "tradeCommodityMarketInfoRepository")
    public CommodityMarketInfoRepository commodityMarketInfoRepository(
            MybatisCommodityMarketInfoRepository mybatisCommodityMarketInfoRepository,
            MybatisCommodityMarketInfoEntityMapper mybatisCommodityMarketInfoEntityMapper) {
        return new CommodityMarketInfoRepository(mybatisCommodityMarketInfoRepository, mybatisCommodityMarketInfoEntityMapper);
    }

    @Bean(name = "tradeCommodityRepository")
    public CommodityRepository commodityRepository(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            MybatisCommodityRepository mybatisCommodityRepository,
            MybatisCommodityEntityMapper mybatisCommodityEntityMapper) {
        return new CommodityRepository(idGenerator, mybatisCommodityEntityMapper, mybatisCommodityRepository);
    }

    @Bean(name = "tradeLocateCommodityRepository")
    public LocateCommodityRepository locateCommodityRepository(
            MybatisLocateCommodityRepository mybatisLocateCommodityRepository,
            MybatisLocateCommodityEntityMapper mybatisLocateCommodityEntityMapper,
            MybatisPersistenceLocateCommodityFilterMapper mybatisPersistenceLocateCommodityFilterMapper) {
        return new LocateCommodityRepository(mybatisLocateCommodityRepository, mybatisLocateCommodityEntityMapper, mybatisPersistenceLocateCommodityFilterMapper);
    }

    @Bean(name = "tradeMarketDatumRepository")
    public MarketDatumRepository marketDatumRepository(
            MybatisMarketDatumRepository mybatisMarketDatumRepository) {
        return new MarketDatumRepository(mybatisMarketDatumRepository);
    }

    @Bean(name = "tradeStationRepository")
    public StationRepository stationRepository(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            MybatisStationRepository mybatisStationRepository,
            MybatisStationEntityMapper mybatisStationEntityMapper,
            MybatisMarketDatumRepository mybatisMarketDatumRepository) {
        return new StationRepository(idGenerator, mybatisStationEntityMapper, mybatisStationRepository, mybatisMarketDatumRepository);
    }

    @Bean(name = "tradeSystemRepository")
    public SystemRepository systemRepository(
            @Qualifier("tradeIdGenerator") IdGenerator idGenerator,
            MybatisSystemRepository mybatisSystemRepository,
            MybatisSystemEntityMapper mybatisSystemEntityMapper) {
        return new SystemRepository(idGenerator, mybatisSystemEntityMapper, mybatisSystemRepository);
    }

    @Bean(name = "tradeValidatedCommodityRepository")
    public ValidatedCommodityRepository validatedCommodityRepository(
            MybatisValidatedCommodityRepository mybatisValidatedCommodityRepository,
            MybatisValidatedCommodityEntityMapper mybatisValidatedCommodityEntityMapper,
            MybatisPersistenceFindCommodityFilterMapper mybatisPersistenceFindCommodityFilterMapper) {
        return new ValidatedCommodityRepository(mybatisValidatedCommodityRepository, mybatisValidatedCommodityEntityMapper, mybatisPersistenceFindCommodityFilterMapper);
    }

    @Bean(name = "tradeSys")
    public SystemCoordinateRequestRepository systemCoordinateRequestRepository(
            MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository) {
        return new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository);
    }
}
