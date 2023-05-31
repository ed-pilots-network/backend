package io.edpn.backend.modulith.commodityfinder.configuration;

import io.edpn.backend.modulith.mybatisutil.StringListTypeHandler;
import io.edpn.backend.modulith.mybatisutil.StringTrimmingTypeHandler;
import io.edpn.backend.modulith.mybatisutil.UuidTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
@Configuration
@MapperScan("io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers")
public class MyBatisConfiguration {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeHandlers(new UuidTypeHandler(), new StringListTypeHandler(), new StringTrimmingTypeHandler());

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
}
