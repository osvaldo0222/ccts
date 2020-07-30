package com.project.ccts.service;

import com.google.gson.Gson;
import com.project.ccts.dto.MqttDTO;
import com.project.ccts.dto.NodeConfigDTO;
import com.project.ccts.model.entities.Node;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.mqtt.MqttConfig;
import com.project.ccts.repository.NodeRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NodeService extends AbstractCrud<Node, Long> {

    private NodeRepository nodeRepository;
    private MqttConfig mqttConfig;

    @Autowired
    public void setNodeRepository(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Autowired
    public void setMqttConfig(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    @Override
    public JpaRepository<Node, Long> getDao() {
        return nodeRepository;
    }

    public Node findByNodeIdentifier(String nodeIdentifier) {
        return nodeRepository.findByNodeIdentifier(nodeIdentifier);
    }

    public void setNodeStatus(String nodeIdentifier, NodeStatus nodeStatus) {
        Node node = findByNodeIdentifier(nodeIdentifier);
        if (node != null) {
            node.setStatus(nodeStatus);
            createOrUpdate(node);
            Logger.getInstance().getLog(getClass()).info(String.format(
                        "%s from %s with description %s is %s.",
                         node.getNodeIdentifier(),
                         node.getLocality().getName(), node.getDescription(),
                         nodeStatus.getStatus()
                    )
            );
        }
    }

    public String getConfiguration(String nodeIdentifier) {
        Node node = findByNodeIdentifier(nodeIdentifier);
        if (node == null) {
            return createMqttMessageToNodes(704, mqttConfig.getClientId(), null);
        }
        NodeConfigDTO nodeConfigDTO = new NodeConfigDTO(node.getLocality().getName(), node.getLocality().getAddress(), node.getDescription(), node.getLocality().getGpsLocation());
        return createMqttMessageToNodes(702, mqttConfig.getClientId(), nodeConfigDTO);
    }

    public String createMqttMessageToNodes(Integer code, String nodeIdentifier, Object data) {
        return new Gson().toJson(new MqttDTO(code,nodeIdentifier, data));
    }
}
