package com.project.ccts.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttOutboundConfig {

    private final MqttConfig mqttConfig;
    private final MqttPahoClientFactory mqttPahoClientFactory;

    @Autowired
    public MqttOutboundConfig(MqttConfig mqttConfig, MqttPahoClientFactory mqttPahoClientFactory) {
        this.mqttConfig = mqttConfig;
        this.mqttPahoClientFactory = mqttPahoClientFactory;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                mqttConfig.getClientId() + "-outbound",
                mqttPahoClientFactory
        );
        messageHandler.setAsync(true);
        messageHandler.setDefaultRetained(false);
        messageHandler.setDefaultTopic(mqttConfig.getUndefinedTopic());
        messageHandler.setDefaultQos(mqttConfig.getQos());
        return messageHandler;
    }
}
