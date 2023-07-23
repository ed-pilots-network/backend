package io.edpn.backend.exploration.configuration;

import liquibase.integration.spring.SpringLiquibase;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration("ExplorationModuleLiquibaseConfig")
public class LiquibaseConfig {

    @Bean(name = "explorationLiquibase")
    public SpringLiquibase liquibase(@Qualifier("explorationDataSource") DataSource dataSource, DataSourceProperties dataSourceProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(dataSourceProperties.getChangeLog());
        liquibase.setShouldRun(dataSourceProperties.isEnabled());
        return liquibase;
    }

    @Configuration("ExplorationModuleLiquibaseProperties")
    @ConfigurationProperties(prefix = "exploration.spring.liquibase")
    @Data
    public static class DataSourceProperties {

        private String changeLog;
        private boolean enabled;
    }
}
