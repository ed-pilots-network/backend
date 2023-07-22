package io.edpn.backend.exploration.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration("ExplorationModuleDataSourceConfig")
public class DataSourceConfig {

    @Bean(name = "explorationDataSource")
    public DataSource getDataSource(@Qualifier("explorationDataSourceProperties") DataSourceProperties dataSourceProperties) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(dataSourceProperties.getDriverClassName());
        dataSourceBuilder.url(dataSourceProperties.getUrl());
        dataSourceBuilder.username(dataSourceProperties.getUsername());
        dataSourceBuilder.password(dataSourceProperties.getPassword());
        return dataSourceBuilder.build();
    }

    @Configuration("ExplorationModuleDataSourceProperties")
    @ConfigurationProperties(prefix = "exploration.spring.datasource")
    @Data
    public static class DataSourceProperties {

        private String url;
        private String username;
        private String password;
        private String driverClassName;
        
        @Bean(name = "explorationDataSourceProperties")
        public DataSourceProperties dataSourceProperties() {
            return this;
        }
    }
}
