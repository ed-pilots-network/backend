package io.edpn.backend.eddnmessagelistener.configuration;

import io.edpn.backend.eddnmessagelistener.infrastructure.zmq.EddnMessageHandler;
import io.edpn.backend.eddnmessagelistener.infrastructure.zmq.OnlyNewMessageSelector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSelector;
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

    @Bean
    @Filter(inputChannel = "inboundEddnChannel", outputChannel = "onlyNewMessagesEddnChannel")
    public MessageSelector onlyNewMessageSelector() {
        return new OnlyNewMessageSelector();
    }

    @Bean(name = "inboundEddnChannel")
    public ZeroMqChannel inboundEddnChannel(ZContext context, @Value("${eddn.uri}") String eddnUri) {
        ZeroMqChannel channel = new ZeroMqChannel(context, true);
        channel.setConnectUrl(eddnUri);
        return channel;
    }

    @Bean(name = "onlyNewMessagesEddnChannel")
    public DirectChannel onlyNewMessagesEddnChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "onlyNewMessagesEddnChannel")
    public MessageHandler messageHandler(EddnMessageHandler eddnMessageHandler) {
        return eddnMessageHandler;
    }

}
