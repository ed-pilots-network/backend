package io.edpn.backend.trade.application.mappers.v1;

import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.CommodityType;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class FindCommodityDTOMapperTest {
    
    private FindCommodityDTOMapper mapper;
    
    @BeforeEach
    void setUp() {
        mapper = new FindCommodityDTOMapper();
    }
    
    @Test
    void shouldMapValidatedCommodityToResponse() {
        

        ValidatedCommodity validatedCommodity = ValidatedCommodity.builder()
                .commodityName("commodityname")
                .displayName("Commodity Name")
                .type(CommodityType.CONSUMER_ITEMS)
                .isRare(true)
                .build();
        

        FindCommodityResponse response = mapper.map(validatedCommodity);
        

        assertThat(response, is(notNullValue()));
        assertThat(response.getDisplayName(), is(equalTo(validatedCommodity.getDisplayName())));
        assertThat(response.getCommodityName(), is(equalTo(validatedCommodity.getCommodityName())));
        assertThat(response.getDisplayName(), is(equalTo(validatedCommodity.getDisplayName())));
        assertThat(response.getType(), is(equalTo(validatedCommodity.getType().toString())));
        assertThat(response.getIsRare(), is(equalTo(validatedCommodity.getIsRare())));

    }
    
    @Test
    void shouldMapFindCommodityRequestToFilter() {

        FindCommodityRequest request = FindCommodityRequest.builder()
                .type("CONSUMER_ITEMS")
                .isRare(false)
                .build();
        

        FindCommodityFilter filter = mapper.map(request);
        

        assertThat(filter, is(notNullValue()));
        assertThat(filter.getType(), is(equalTo(CommodityType.valueOf(request.getType()))));
        assertThat(filter.getIsRare(), is(equalTo(request.getIsRare())));
    }
}
