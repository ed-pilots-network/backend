package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.persistence.CommodityMarketInfoRepository;
import io.edpn.backend.trade.adapter.persistence.CommodityRepository;
import io.edpn.backend.trade.adapter.persistence.LatestMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.LocateCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.MarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.adapter.persistence.StationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.adapter.persistence.StationPlanetaryRequestRepository;
import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.StationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.adapter.persistence.SystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.SystemEliteIdRequestRepository;
import io.edpn.backend.trade.adapter.persistence.SystemRepository;
import io.edpn.backend.trade.adapter.persistence.ValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.CommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.CommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.SystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindStationFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.LocateCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityMarketInfoRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLatestMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationLandingPadSizeRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationPlanetaryRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisValidatedCommodityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeRepositoryConfig")
public class RepositoryConfig {

    @Bean(name = "tradeCommodityMarketInfoRepository")
    public CommodityMarketInfoRepository commodityMarketInfoRepository(
            MybatisCommodityMarketInfoRepository mybatisCommodityMarketInfoRepository,
            CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper) {
        return new CommodityMarketInfoRepository(mybatisCommodityMarketInfoRepository, commodityMarketInfoEntityMapper);
    }

    @Bean(name = "tradeCommodityRepository")
    public CommodityRepository commodityRepository(
            MybatisCommodityRepository mybatisCommodityRepository,
            CommodityEntityMapper commodityEntityMapper) {
        return new CommodityRepository(commodityEntityMapper, mybatisCommodityRepository);
    }

    @Bean(name = "tradeLocateCommodityRepository")
    public LocateCommodityRepository locateCommodityRepository(
            MybatisLocateCommodityRepository mybatisLocateCommodityRepository,
            LocateCommodityEntityMapper locateCommodityEntityMapper,
            LocateCommodityFilterMapper locateCommodityFilterMapper) {
        return new LocateCommodityRepository(mybatisLocateCommodityRepository, locateCommodityEntityMapper, locateCommodityFilterMapper);
    }

    @Bean(name = "tradeMarketDatumRepository")
    public MarketDatumRepository marketDatumRepository(
            MybatisMarketDatumRepository mybatisMarketDatumRepository,
            MarketDatumEntityMapper marketDatumEntityMapper) {
        return new MarketDatumRepository(mybatisMarketDatumRepository, marketDatumEntityMapper);
    }

    @Bean(name = "tradeLatestMarketDatumRepository")
    public LatestMarketDatumRepository latestMarketDatumRepository(
            MybatisLatestMarketDatumRepository mybatisLatestMarketDatumRepository,
            MarketDatumEntityMapper marketDatumEntityMapper) {
        return new LatestMarketDatumRepository(mybatisLatestMarketDatumRepository, marketDatumEntityMapper);
    }

    @Bean(name = "tradeStationRepository")
    public StationRepository stationRepository(
            MybatisStationRepository mybatisStationRepository,
            StationEntityMapper stationEntityMapper,
            FindStationFilterMapper persistenceFindStationFilterMapper) {
        return new StationRepository(stationEntityMapper, mybatisStationRepository, persistenceFindStationFilterMapper);
    }

    @Bean(name = "tradeSystemRepository")
    public SystemRepository systemRepository(
            MybatisSystemRepository mybatisSystemRepository,
            FindSystemFilterMapper findSystemFilterMapper,
            SystemEntityMapper systemEntityMapper) {
        return new SystemRepository(systemEntityMapper, findSystemFilterMapper, mybatisSystemRepository);
    }

    @Bean(name = "tradeValidatedCommodityRepository")
    public ValidatedCommodityRepository validatedCommodityRepository(
            MybatisValidatedCommodityRepository mybatisValidatedCommodityRepository,
            ValidatedCommodityEntityMapper validatedCommodityEntityMapper,
            FindCommodityFilterMapper findCommodityFilterMapper) {
        return new ValidatedCommodityRepository(mybatisValidatedCommodityRepository, validatedCommodityEntityMapper, findCommodityFilterMapper);
    }

    @Bean(name = "tradeSystemCoordinateRequestRepository")
    public SystemCoordinateRequestRepository systemCoordinateRequestRepository(
            MybatisSystemCoordinateRequestRepository mybatisSystemCoordinateRequestRepository,
            SystemDataRequestEntityMapper systemDataRequestEntityMapper) {
        return new SystemCoordinateRequestRepository(mybatisSystemCoordinateRequestRepository, systemDataRequestEntityMapper);
    }

    @Bean(name = "tradeStationRequireOdysseyRequestRepository")
    public StationRequireOdysseyRequestRepository stationRequireOdysseyRequestRepository(
            MybatisStationRequireOdysseyRequestRepository mybatisStationRequireOdysseyRequestRepository,
            StationDataRequestEntityMapper stationDataRequestEntityMapper) {
        return new StationRequireOdysseyRequestRepository(mybatisStationRequireOdysseyRequestRepository, stationDataRequestEntityMapper);
    }

    @Bean(name = "tradeSystemEliteIdRequestRepository")
    public SystemEliteIdRequestRepository systemEliteIdRequestRepository(
            MybatisSystemEliteIdRequestRepository mybatisSystemEliteIdRequestRepository,
            SystemDataRequestEntityMapper systemDataRequestEntityMapper) {
        return new SystemEliteIdRequestRepository(mybatisSystemEliteIdRequestRepository, systemDataRequestEntityMapper);
    }

    @Bean(name = "tradeStationArrivalDistanceRequestRepository")
    public StationArrivalDistanceRequestRepository stationArrivalDistanceRequestRepository(
            MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository,
            StationDataRequestEntityMapper stationDataRequestEntityMapper) {
        return new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository, stationDataRequestEntityMapper);
    }

    @Bean(name = "tradeStationPlanetaryRequestRepository")
    public StationPlanetaryRequestRepository stationPlanetaryRequestRepository(
            MybatisStationPlanetaryRequestRepository mybatisStationPlanetaryRequestRepository,
            StationDataRequestEntityMapper stationDataRequestEntityMapper) {
        return new StationPlanetaryRequestRepository(mybatisStationPlanetaryRequestRepository, stationDataRequestEntityMapper);
    }

    @Bean(name = "tradeStationLandingPadSizeRequestRepository")
    public StationLandingPadSizeRequestRepository stationLandingPadSizeRequestRepository(
            MybatisStationLandingPadSizeRequestRepository mybatisStationLandingPadSizeRequestRepository,
            StationDataRequestEntityMapper stationDataRequestEntityMapper) {
        return new StationLandingPadSizeRequestRepository(mybatisStationLandingPadSizeRequestRepository, stationDataRequestEntityMapper);
    }
}
