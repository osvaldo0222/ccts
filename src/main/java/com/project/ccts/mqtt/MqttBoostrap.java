package com.project.ccts.mqtt;

import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import static com.project.ccts.mqtt.MqttUtil.createMqttMessageToNodes;

@Component
public class MqttBoostrap implements ApplicationListener<ContextRefreshedEvent> {

    private final OutboundMqttGateway outboundMqttGateway;
    private final MqttConfig mqttConfig;

    @Autowired
    public MqttBoostrap(OutboundMqttGateway outboundMqttGateway, MqttConfig mqttConfig) {
        this.outboundMqttGateway = outboundMqttGateway;
        this.mqttConfig = mqttConfig;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        outboundMqttGateway.sendToMqtt(
                createMqttMessageToNodes(MqttCctsCodes.WEB_SERVER_CONNECT, mqttConfig.getClientId(), null),
                mqttConfig.getWebServerStatus()
        );
        Logger.getInstance().getLog(getClass()).info("Advertising to the nodes the connection of the server...");
    }
}
