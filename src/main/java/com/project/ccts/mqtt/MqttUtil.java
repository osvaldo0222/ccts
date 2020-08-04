package com.project.ccts.mqtt;

import com.google.gson.Gson;
import com.project.ccts.dto.MqttDTO;

public class MqttUtil {

    public static String createMqttMessageToNodes(MqttCctsCodes code, String nodeIdentifier, Object data) {
        return new Gson().toJson(new MqttDTO(
                code.getCode(),
                nodeIdentifier,
                data == null ? code.getDescription() : data)
        );
    }
}
