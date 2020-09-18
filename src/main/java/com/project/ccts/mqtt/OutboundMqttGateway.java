package com.project.ccts.mqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface OutboundMqttGateway {

    void sendToMqtt(String data);

    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);

    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos);

    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, @Header(MqttHeaders.RETAINED) Boolean retain);

}
