package io.eddb.eddb2backend.configuration;

import io.eddb.eddb2backend.application.dto.persistence.*;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.*;
import io.eddb.eddb2backend.infrastructure.persistence.util.CommodityEntityIdTypeHandler;
import io.eddb.eddb2backend.infrastructure.persistence.util.AbstractEntityIdTypeHandler;
import io.eddb.eddb2backend.infrastructure.persistence.util.EconomyEntityIdTypeHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("io.eddb.eddb2backend.infrastructure.persistence.mappers")
public class MyBatisConfiguration {

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public SystemEntityMapper systemEntityMapper(@Qualifier("sqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        return sqlSessionTemplate.getMapper(SystemEntityMapper.class);
    }

    @Bean
    public StationEntityMapper stationEntityMapper(@Qualifier("sqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        return sqlSessionTemplate.getMapper(StationEntityMapper.class);
    }

    @Bean
    public CommodityEntityMapper commodityEntityMapper(@Qualifier("sqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        return sqlSessionTemplate.getMapper(CommodityEntityMapper.class);
    }

    @Bean
    public EconomyEntityMapper economyEntityMapper(@Qualifier("sqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        return sqlSessionTemplate.getMapper(EconomyEntityMapper.class);
    }

    @Bean
    public HistoricStationCommodityEntityMapper historicStationCommodityEntityMapper(@Qualifier("sqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        return sqlSessionTemplate.getMapper(HistoricStationCommodityEntityMapper.class);
    }

    @Bean
    public StationTypeEntityMapper stationTypeEntityMapper(@Qualifier("sqlSessionTemplate") SqlSessionTemplate sqlSessionTemplate) {
        return sqlSessionTemplate.getMapper(StationTypeEntityMapper.class);
    }
}
