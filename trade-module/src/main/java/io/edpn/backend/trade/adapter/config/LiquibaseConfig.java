package io.edpn.backend.trade.adapter.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration("TradeModuleLiquibaseConfig")
public class LiquibaseConfig {

    @Bean(name = "tradeLiquibase")
    public SpringLiquibase liquibase(@Qualifier("tradeDataSource") DataSource dataSource, DataSourceProperties dataSourceProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(dataSourceProperties.getChangeLog());
        liquibase.setShouldRun(dataSourceProperties.isEnabled());
        return liquibase;
    }

    @Configuration("TradeModuleLiquibaseProperties")
    @ConfigurationProperties(prefix = "trade.spring.liquibase")
    @Data
    public static class DataSourceProperties {

        private String changeLog;
        private boolean enabled;
    }
}