module edpn.lib {
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires edpn.util;


    exports io.edpn.backend.modulith.messageprocessorlib.application.dto.eddn;
    exports io.edpn.backend.modulith.messageprocessorlib.infrastructure.kafka.processor;
}