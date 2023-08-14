package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisValidatedCommodityEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeModuleEntityMapperConfig")
public class PersistenceEntityMapperConfig {
    
    @Bean(name = "tradeValidatedCommodityEntityMapper")
    public MybatisValidatedCommodityEntityMapper validatedCommodityEntityMapper() {
        return new MybatisValidatedCommodityEntityMapper();
    }
    
    @Bean(name = "tradeFindCommodityEntityMapper")
    public MybatisPersistenceFindCommodityFilterMapper findCommodityMapper() {
        return new MybatisPersistenceFindCommodityFilterMapper();
    }
}
