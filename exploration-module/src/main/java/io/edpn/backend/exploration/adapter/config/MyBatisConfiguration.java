package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.persistence.MybatisBodyRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisRingRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisStarRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemEliteIdRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
import io.edpn.backend.mybatisutil.IntegerStringMapTypeHandler;
import io.edpn.backend.mybatisutil.StringDoubleMapTypeHandler;
import io.edpn.backend.mybatisutil.StringListToArrayTypeHandler;
import io.edpn.backend.mybatisutil.StringTrimmingTypeHandler;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
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
        sessionFactoryBean.setTypeHandlers(new UuidTypeHandler(), new StringTrimmingTypeHandler(), new StringListToArrayTypeHandler(), new StringDoubleMapTypeHandler(), new IntegerStringMapTypeHandler());

        return sessionFactoryBean.getObject();
    }

    @Bean(name = "explorationMybatisSystemRepository")
    public MapperFactoryBean<MybatisSystemRepository> mybatisSystemRepository(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MybatisSystemRepository> factoryBean = new MapperFactoryBean<>(MybatisSystemRepository.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean(name = "explorationMybatisSystemCoordinateRequestRepository")
    public MapperFactoryBean<MybatisSystemCoordinateRequestRepository> mybatisSystemCoordinateRequestRepository(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MybatisSystemCoordinateRequestRepository> factoryBean = new MapperFactoryBean<>(MybatisSystemCoordinateRequestRepository.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean(name = "explorationMybatisSystemEliteIdRequestRepository")
    public MapperFactoryBean<MybatisSystemEliteIdRequestRepository> mybatisSystemEliteIdRequestRepository(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MybatisSystemEliteIdRequestRepository> factoryBean = new MapperFactoryBean<>(MybatisSystemEliteIdRequestRepository.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
    
    @Bean(name = "explorationMybatisBodyRepository")
    public MapperFactoryBean<MybatisBodyRepository> mybatisBodyRepository(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MybatisBodyRepository> factoryBean = new MapperFactoryBean<>(MybatisBodyRepository.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
    
    @Bean(name = "explorationMybatisStarRepository")
    public MapperFactoryBean<MybatisStarRepository> mybatisStarRepository(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MybatisStarRepository> factoryBean = new MapperFactoryBean<>(MybatisStarRepository.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
    
    @Bean(name = "explorationMybatisRingRepository")
    public MapperFactoryBean<MybatisRingRepository> mybatisRingRepository(@Qualifier("explorationSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MybatisRingRepository> factoryBean = new MapperFactoryBean<>(MybatisRingRepository.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
