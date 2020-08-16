package com.project.ccts.mqtt;

import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import static com.project.ccts.mqtt.MqttUtil.createMqttMessageToNodes;

@Component
public class MqttStopEvent implements ApplicationListener<ContextClosedEvent> {

    private final OutboundMqttGateway outboundMqttGateway;
    private final MqttConfig mqttConfig;

    @Autowired
    public MqttStopEvent(OutboundMqttGateway outboundMqttGateway, MqttConfig mqttConfig) {
        this.outboundMqttGateway = outboundMqttGateway;
        this.mqttConfig = mqttConfig;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        outboundMqttGateway.sendToMqtt(
                createMqttMessageToNodes(MqttCctsCodes.WEB_SERVER_DISCONNECT, mqttConfig.getClientId(), null),
                mqttConfig.getWebServerStatus()
        );
        Logger.getInstance().getLog(getClass()).info("Advertising to the nodes the disconnection of the server...");
    }
}
