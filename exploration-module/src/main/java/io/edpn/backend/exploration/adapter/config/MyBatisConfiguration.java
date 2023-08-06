package io.edpn.backend.exploration.adapter.config;

import io.edpn.backend.exploration.adapter.persistence.MybatisSystemCoordinateRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository;
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
        sessionFactoryBean.setTypeHandlers(new UuidTypeHandler(), new StringTrimmingTypeHandler(), new StringListToArrayTypeHandler());

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
}
