package io.edpn.backend.exploration.configuration;

import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.RequestDataMessageEntityMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemCoordinateDataRequestEntityMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.mybatisutil.StringListToArrayTypeHandler;
import io.edpn.backend.mybatisutil.StringTrimmingTypeHandler;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.util.IdGenerator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration("ExplorationModuleMyBatisConfiguration")
public class MyBatisConfiguration {

    @Bean(name = "explorationSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("explorationDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeHandlers(new UuidTypeHandler(), new StringTrimmingTypeHandler(), new StringListToArrayTypeHandler());

        return sessionFactoryBean.getObject();
    }

    @Bean(name = "explorationIdGenerator")
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean(name = "explorationSystemEntityMapper")
    public MapperFactoryBean<SystemEntityMapper> systemEntityMapper(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<SystemEntityMapper> factoryBean = new MapperFactoryBean<>(SystemEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean(name = "explorationRequestDataMessageEntityMapper")
    public MapperFactoryBean<RequestDataMessageEntityMapper> requestDataMessageEntityMapper(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<RequestDataMessageEntityMapper> factoryBean = new MapperFactoryBean<>(RequestDataMessageEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean(name = "explorationSystemCoordinateDataRequestEntityMapper")
    public MapperFactoryBean<SystemCoordinateDataRequestEntityMapper> systemCoordinateDataRequestEntityMapper(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<SystemCoordinateDataRequestEntityMapper> factoryBean = new MapperFactoryBean<>(SystemCoordinateDataRequestEntityMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
