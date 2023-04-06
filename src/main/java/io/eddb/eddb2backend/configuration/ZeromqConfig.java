package io.eddb.eddb2backend.configuration;

import io.eddb.eddb2backend.infrastructure.external.EddnMessageHandler;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.zeromq.channel.ZeroMqChannel;
import org.springframework.messaging.MessageHandler;
import org.zeromq.ZContext;

@Configuration
@EnableIntegration
public class ZeromqConfig {

    @Bean
    public ZContext zContext() {
        return new ZContext();
    }

    @Bean(name = "zeroMqChannel")
    ZeroMqChannel zeroMqPubSubChannel(ZContext context) {
        ZeroMqChannel channel = new ZeroMqChannel(context, true);
        channel.setConnectUrl("tcp://eddn.edcd.io:9500:9500");
        return channel;
    }


    @Bean
    @ServiceActivator(inputChannel = "zeroMqChannel")
    public EddnMessageHandler eddnMessageHandler() {
        return new EddnMessageHandler();
    }

}
