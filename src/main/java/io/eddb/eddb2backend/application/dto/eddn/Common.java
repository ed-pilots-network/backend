package io.eddb.eddb2backend.application.dto.eddn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface Common {
    @Data
    @NoArgsConstructor
    class EddnMessageHeader {
        @JsonProperty("uploaderID")
        private String uploaderID;
        @JsonProperty("gameversion")
        private String gameVersion;
        @JsonProperty("gamebuild")
        private String gameBuild;
        @JsonProperty("softwareName")
        private String softwareName;
        @JsonProperty("softwareVersion")
        private String softwareVersion;
        @JsonProperty("gatewayTimestamp")
        private String gatewayTimestamp;
    }
}
