package io.edpn.backend.trade.application.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class FindCommodityRequest {
    
    @NotNull(message = "Type is mandatory")
    @Schema(example = "SMALL", allowableValues = " CHEMICALS, CONSUMER_ITEMS, LEGAL_DRUGS, FOODS, INDUSTRIAL_MATERIALS, " +
            "MACHINERY, MEDICINES, METALS, MINERALS, SALVAGE, SLAVES, TECHNOLOGY, TEXTILES, WASTE, WEAPONS")
    String type;
    Boolean isRare;
}
