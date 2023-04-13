package io.eddb.eddb2backend.application.dto.eddn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface CommodityMessage {
    @Data
    @NoArgsConstructor
    class V3 {
        @JsonProperty("$schemaRef")
        private String schemaRef;
        @JsonProperty("header")
        private Common.EddnMessageHeader header;
        @JsonProperty("message")
        private Message message;

        @Data
        @NoArgsConstructor
        public static class Message {
            @JsonProperty("systemName")
            private String systemName;
            @JsonProperty("stationName")
            private String stationName;
            @JsonProperty("marketId")
            private long marketId;
            @JsonProperty("horizons")
            private boolean horizons;
            @JsonProperty("odyssey")
            private boolean odyssey;
            @JsonProperty("timestamp")
            private String timestamp;
            @JsonProperty("economies")
            private Economy[] economies;
            @JsonProperty("prohibited")
            private String[] prohibited;
            @JsonProperty("commodities")
            private Commodity[] commodities;
        }

        @Data
        @NoArgsConstructor
        public static class Commodity {
            @JsonProperty("name")
            private String name;
            @JsonProperty("meanPrice")
            private int meanPrice;
            @JsonProperty("buyPrice")
            private int buyPrice;
            @JsonProperty("stock")
            private int stock;
            @JsonProperty("stockBracket")
            private int stockBracket;
            @JsonProperty("sellPrice")
            private int sellPrice;
            @JsonProperty("demand")
            private int demand;
            @JsonProperty("demandBracket")
            private int demandBracket;
            @JsonProperty("statusFlags")
            private String[] statusFlags;
        }

        @Data
        @NoArgsConstructor
        public static class Economy {
            @JsonProperty("name")
            private String name;
            @JsonProperty("proportion")
            private double proportion;
        }
    }
}
