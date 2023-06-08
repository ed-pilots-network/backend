package io.edpn.backend.commodityfinder.configuration;

import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityMarketInfoEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.StationEntityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.mybatisutil.StringListToArrayTypeHandler;
import io.edpn.backend.mybatisutil.StringTrimmingTypeHandler;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.util.IdGenerator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration("CommodityFinderMyBatisConfiguration")
public class MyBatisConfiguration {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeHandlers(new UuidTypeHandler(), new StringTrimmingTypeHandler(), new StringListToArrayTypeHandler());

        return sessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean
    public MapperFactoryBean<CommodityEntityMapper> commodityEntityMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<CommodityEntityMapper> factoryBean = new MapperFactoryBean<>(CommodityEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<MarketDatumEntityMapper> marketDatumEntityMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MarketDatumEntityMapper> factoryBean = new MapperFactoryBean<>(MarketDatumEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<StationEntityMapper> stationEntityMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<StationEntityMapper> factoryBean = new MapperFactoryBean<>(StationEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<SystemEntityMapper> systemEntityMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<SystemEntityMapper> factoryBean = new MapperFactoryBean<>(SystemEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<CommodityMarketInfoEntityMapper> bestCommodityPriceEntityMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<CommodityMarketInfoEntityMapper> factoryBean = new MapperFactoryBean<>(CommodityMarketInfoEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
