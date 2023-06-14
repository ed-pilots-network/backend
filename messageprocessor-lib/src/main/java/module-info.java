module edpn.messageprocessor.lib {
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires edpn.util;

    exports io.edpn.backend.messageprocessorlib.application.dto.eddn;
    exports io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor;
    exports io.edpn.backend.messageprocessorlib.application.dto.eddn.data;
}
