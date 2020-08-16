package com.project.ccts.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttInboundConfig {

    private final MqttConfig mqttConfig;
    private final MqttPahoClientFactory mqttPahoClientFactory;

    @Autowired
    public MqttInboundConfig(MqttConfig mqttConfig, MqttPahoClientFactory mqttPahoClientFactory) {
        this.mqttConfig = mqttConfig;
        this.mqttPahoClientFactory = mqttPahoClientFactory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                mqttConfig.getClientId() + "-inbound",
                mqttPahoClientFactory,
                mqttConfig.getConfigTopic(),
                mqttConfig.getNodesTopic()
        );
        adapter.setCompletionTimeout(mqttConfig.getCompletionTimeout());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(mqttConfig.getQos());
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }
}
