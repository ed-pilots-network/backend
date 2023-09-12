package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisLocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisMarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisPageInfoMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisSystemEntityMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisValidatedCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindStationFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindSystemFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistencePageFilterMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.PersistencePageInfoMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindStationFilterMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindSystemFilterMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistencePageFilterMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleEntityMapperConfig")
public class PersistenceEntityMapperConfig {

    @Bean(name = "tradeValidatedCommodityEntityMapper")
    public MybatisValidatedCommodityEntityMapper validatedCommodityEntityMapper() {
        return new MybatisValidatedCommodityEntityMapper();
    }

    @Bean(name = "tradeFindCommodityEntityMapper")
    public MybatisPersistenceFindCommodityFilterMapper findCommodityFilterMapper() {
        return new MybatisPersistenceFindCommodityFilterMapper();
    }

    @Bean(name = "tradeLocateCommodityEntityMapper")
    public MybatisPersistenceLocateCommodityFilterMapper locateCommodityFilterMapper(PersistencePageFilterMapper persistencePageFilterMapper) {
        return new MybatisPersistenceLocateCommodityFilterMapper(persistencePageFilterMapper);
    }

    @Bean(name = "tradePersistencePageFilterMapper")
    public MybatisPersistencePageFilterMapper persistencePageFilterMapper() {
        return new MybatisPersistencePageFilterMapper();
    }

    @Bean(name = "tradePersistenceFindSystemFilterMapper")
    public PersistenceFindSystemFilterMapper persistenceFindSystemFilterMapper() {
        return new MybatisPersistenceFindSystemFilterMapper();
    }

    @Bean(name = "tradeSystemEntityMapper")
    public MybatisSystemEntityMapper systemEntityMapper() {
        return new MybatisSystemEntityMapper();
    }

    @Bean(name = "tradeCommodityEntityMapper")
    public MybatisCommodityEntityMapper commodityEntityMapper() {
        return new MybatisCommodityEntityMapper();
    }

    @Bean(name = "tradeMarketDatumEntityMapper")
    public MybatisMarketDatumEntityMapper marketDatumEntityMapper(
            MybatisCommodityEntityMapper mybatisCommodityEntityMapper) {
        return new MybatisMarketDatumEntityMapper(mybatisCommodityEntityMapper);
    }

    @Bean(name = "tradeStationEntityMapper")
    public MybatisStationEntityMapper stationEntityMapper(
            MybatisSystemEntityMapper mybatisSystemEntityMapper,
            MybatisMarketDatumEntityMapper mybatisMarketDatumEntityMapper) {
        return new MybatisStationEntityMapper(mybatisSystemEntityMapper, mybatisMarketDatumEntityMapper);
    }

    @Bean(name = "tradeLocateCommodityFilterMapper")
    public MybatisLocateCommodityEntityMapper locateCommodityEntityMapper(
            MybatisValidatedCommodityEntityMapper mybatisValidatedCommodityEntityMapper,
            MybatisStationEntityMapper mybatisStationEntityMapper) {
        return new MybatisLocateCommodityEntityMapper(mybatisValidatedCommodityEntityMapper, mybatisStationEntityMapper);
    }

    @Bean(name = "tradeCommodityMarketInfoEntityMapper")
    public MybatisCommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper(
            MybatisValidatedCommodityEntityMapper mybatisValidatedCommodityEntityMapper,
            MybatisStationEntityMapper mybatisStationEntityMapper) {
        return new MybatisCommodityMarketInfoEntityMapper(mybatisValidatedCommodityEntityMapper, mybatisStationEntityMapper);
    }

    @Bean(name = "tradePersistenceFindStationFilterMapper")
    public PersistenceFindStationFilterMapper persistenceFindStationFilterMapper() {
        return new MybatisPersistenceFindStationFilterMapper();
    }

    @Bean(name = "tradePersistencePageInfoMapper")
    public PersistencePageInfoMapper persistencePageInfoMapper() {
        return new MybatisPageInfoMapper();
    }


}
