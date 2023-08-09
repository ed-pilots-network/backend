package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.mybatisutil.StringListToArrayTypeHandler;
import io.edpn.backend.mybatisutil.StringTrimmingTypeHandler;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.trade.adapter.persistence.MybatisValidatedCommodityRepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    
    @Bean(name = "tradeMybatisValidatedCommodityRepository")
    public MapperFactoryBean<MybatisValidatedCommodityRepository> mybatisValidatedCommodityRepository(@Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MybatisValidatedCommodityRepository> factoryBean = new MapperFactoryBean<>(MybatisValidatedCommodityRepository.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

}
