package io.edpn.backend.messageprocessorlib.application.dto.eddn;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.edpn.backend.util.TimestampConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public interface ApproachSettlement {
    @Data
    @NoArgsConstructor
    class V1 implements withMessageTimestamp {
        @JsonProperty("$schemaRef")
        private String schemaRef;
        @JsonProperty("header")
        private Common.EddnMessageHeader header;
        @JsonProperty("message")
        private Message message;

        @Override
        public LocalDateTime getMessageTimeStamp() {
            return TimestampConverter.convertToLocalDateTime(message.getTimestamp());
        }

        @Data
        @NoArgsConstructor
        public static class Message {
            @JsonProperty("SystemAddress")
            private long systemAddress;
            @JsonProperty("StarSystem")
            private String starSystem;
            @JsonProperty("StarPos")
            private double[] starPos;
            @JsonProperty("MarketID")
            private long marketId;
            @JsonProperty("horizons")
            private boolean horizons;
            @JsonProperty("odyssey")
            private boolean odyssey;
            @JsonProperty("event")
            private Event event;
            @JsonProperty("BodyID")
            private long bodyId;
            @JsonProperty("BodyName")
            private String bodyName;
            @JsonProperty("Name")
            private String name;
            @JsonProperty("Latitude")
            private long latitude;
            @JsonProperty("Longitude")
            private long longitude;
            @JsonProperty("timestamp")
            private String timestamp;

        }

        public enum Event {
            ApproachSettlement
        }
    }
}
