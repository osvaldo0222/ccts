package com.project.ccts.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.project.ccts.dto.MqttDTO;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.service.NodeService;
import com.project.ccts.service.VisitService;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttControllerConfig {

    private final OutboundMqttGateway outboundMqttGateway;
    private final MqttConfig mqttConfig;
    private final NodeService nodeService;
    private final VisitService visitService;

    @Autowired
    public MqttControllerConfig(OutboundMqttGateway outboundMqttGateway, MqttConfig mqttConfig, NodeService nodeService, VisitService visitService) {
        this.outboundMqttGateway = outboundMqttGateway;
        this.mqttConfig = mqttConfig;
        this.nodeService = nodeService;
        this.visitService = visitService;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return (message) -> {
            try {
                MqttDTO mqttDTO = new Gson().fromJson(message.getPayload().toString(), MqttDTO.class);
                if (mqttDTO.getNodeIdentifier().equals(mqttConfig.getClientId())) {
                    return;
                }
                mqttDispatcher(mqttDTO);
            } catch (JsonSyntaxException e) {
                Logger.getInstance().getLog(getClass()).warn(String.format("Error parsing mqtt message to ccts protocol - %s", e.getMessage()));
            }
        };
    }

    public void mqttDispatcher(MqttDTO mqttDTO) {
        switch (mqttDTO.getCode()) {
            case 700:
                //Node disconnect
                nodeService.setNodeStatus(mqttDTO.getNodeIdentifier(), NodeStatus.DOWN);
                break;
            case 701:
                //Node comes up
                nodeService.setNodeStatus(mqttDTO.getNodeIdentifier(), NodeStatus.ACTIVE);
                outboundMqttGateway.sendToMqtt(nodeService.getConfiguration(mqttDTO.getNodeIdentifier()), String.format("/config/%s", mqttDTO.getNodeIdentifier()));
                break;
            case 702:
                //Server send config
                //Nothing to do on server
                break;
            case 704:
                //Node not found
                //Nothing to do on server
                break;
            case 705:
                //Visits
                visitService.addVisits(mqttDTO);
                System.out.println(mqttDTO);
                break;
            default:
                break;
        }
    }
}
