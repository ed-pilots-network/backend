package io.edpn.backend.trade.adapter.config;

import io.edpn.backend.trade.adapter.persistence.repository.MybatisValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.ValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisValidatedCommodityEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TradeRepositoryConfig")
public class RepositoryConfig {
    
    @Bean(name = "tradeValidatedCommodityRepository")
    public ValidatedCommodityRepository validatedCommodityRepository(
            MybatisValidatedCommodityRepository mybatisValidatedCommodityRepository,
            MybatisValidatedCommodityEntityMapper mybatisValidatedCommodityEntityMapper) {
        return new ValidatedCommodityRepository(mybatisValidatedCommodityRepository, mybatisValidatedCommodityEntityMapper);
    }
}
