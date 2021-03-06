package com.project.ccts.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

import static com.project.ccts.mqtt.MqttUtil.createMqttMessageToNodes;

@Configuration
public class MqttClientFactoryConfig {

    private final MqttConfig mqttConfig;

    @Autowired
    public MqttClientFactoryConfig(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { mqttConfig.getUrl() });
        options.setUserName(mqttConfig.getUsername());
        options.setPassword(mqttConfig.getPassword().toCharArray());
        options.setAutomaticReconnect(mqttConfig.getAutomaticReconnect());
        options.setWill(
                mqttConfig.getWebServerStatus(),
                createMqttMessageToNodes(MqttCctsCodes.WEB_SERVER_DISCONNECT, mqttConfig.getClientId(), null).getBytes(),
                mqttConfig.getQos(),
                false
        );
        factory.setConnectionOptions(options);
        return factory;
    }
}
