package io.edpn.backend.trade.configuration;

import io.edpn.backend.mybatisutil.StringListToArrayTypeHandler;
import io.edpn.backend.mybatisutil.StringTrimmingTypeHandler;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.LocateCommodityEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.RequestDataMessageEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.StationEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.ValidatedCommodityEntityMapper;
import io.edpn.backend.util.IdGenerator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration("TradeModuleMyBatisConfiguration")
public class MyBatisConfiguration {

    @Bean(name = "tradeSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("tradeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeHandlers(new UuidTypeHandler(), new StringTrimmingTypeHandler(), new StringListToArrayTypeHandler());

        return sessionFactoryBean.getObject();
    }

    @Bean(name = "tradePlatformTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("tradeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "tradeSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "tradeIdGenerator")
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean(name = "tradeCommodityEntityMapper")
    public MapperFactoryBean<CommodityEntityMapper> commodityEntityMapper(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<CommodityEntityMapper> factoryBean = new MapperFactoryBean<>(CommodityEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<MarketDatumEntityMapper> marketDatumEntityMapper(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MarketDatumEntityMapper> factoryBean = new MapperFactoryBean<>(MarketDatumEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<StationEntityMapper> stationEntityMapper(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<StationEntityMapper> factoryBean = new MapperFactoryBean<>(StationEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<SystemEntityMapper> systemEntityMapper(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<SystemEntityMapper> factoryBean = new MapperFactoryBean<>(SystemEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<CommodityMarketInfoEntityMapper> bestCommodityPriceEntityMapper(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<CommodityMarketInfoEntityMapper> factoryBean = new MapperFactoryBean<>(CommodityMarketInfoEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<LocateCommodityEntityMapper> locateCommodityEntityMapper(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<LocateCommodityEntityMapper> factoryBean = new MapperFactoryBean<>(LocateCommodityEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<ValidatedCommodityEntityMapper> findCommodityEntityMapper(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<ValidatedCommodityEntityMapper> factoryBean = new MapperFactoryBean<>(ValidatedCommodityEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<RequestDataMessageEntityMapper> requestDataMessageEntityMapper(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<RequestDataMessageEntityMapper> factoryBean = new MapperFactoryBean<>(RequestDataMessageEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
