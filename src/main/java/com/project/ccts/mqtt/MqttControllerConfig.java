package com.project.ccts.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.project.ccts.dto.MqttDTO;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.service.NodeService;
import com.project.ccts.service.ProjectStatisticsService;
import com.project.ccts.service.VisitService;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import static com.project.ccts.mqtt.MqttCctsCodes.getByCode;

@Configuration
public class MqttControllerConfig {

    private final OutboundMqttGateway outboundMqttGateway;
    private final MqttConfig mqttConfig;
    private final NodeService nodeService;
    private final VisitService visitService;
    private final ProjectStatisticsService projectStatisticsService;

    @Autowired
    public MqttControllerConfig(OutboundMqttGateway outboundMqttGateway, MqttConfig mqttConfig, NodeService nodeService, VisitService visitService, ProjectStatisticsService projectStatisticsService) {
        this.outboundMqttGateway = outboundMqttGateway;
        this.mqttConfig = mqttConfig;
        this.nodeService = nodeService;
        this.visitService = visitService;
        this.projectStatisticsService = projectStatisticsService;
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
                projectStatisticsService.addMessageReceivedNodes();
            } catch (JsonSyntaxException e) {
                Logger.getInstance().getLog(getClass()).warn(String.format("Error parsing mqtt message to ccts protocol - %s", e.getMessage()));
            }
        };
    }

    /**
     * Mqtt messages dispatcher for CCTS
     *
     * @param mqttDTO Message received
     */
    public void mqttDispatcher(MqttDTO mqttDTO) {
        switch (getByCode(mqttDTO.getCode())) {
            case NODE_DISCONNECT:
                nodeService.setNodeStatus(mqttDTO.getNodeIdentifier(), NodeStatus.DOWN);
                break;
            case NODE_CONNECT:
                nodeService.setNodeStatus(mqttDTO.getNodeIdentifier(), NodeStatus.ACTIVE);
                outboundMqttGateway.sendToMqtt(
                        nodeService.getConfiguration(mqttDTO.getNodeIdentifier()),
                        String.format("/config/%s", mqttDTO.getNodeIdentifier())
                );
                break;
            case WEB_SERVER_SEND_CONFIG:
                //Nothing to do on server
                Logger.getInstance().getLog(getClass()).info("Server send config to the last node connected!");
                break;
            case WEB_SERVER_DISCONNECT:
                //Nothing to do on server
                Logger.getInstance().getLog(getClass()).info("Server is down!");
                break;
            case NODE_NOT_FOUND:
                //Nothing to do on server
                Logger.getInstance().getLog(getClass()).info("Node not found on DB!");
                break;
            case VISIT_MESSAGE_FROM_NODE:
                visitService.addVisits(mqttDTO);
                break;
            default:
                Logger.getInstance().getLog(getClass()).info(
                        String.format(
                                "Unhandled MQTT CCTS code dispatch. Code: %s.",
                                mqttDTO.getCode().toString()
                        )
                );
                break;
        }
    }
}
