package com.project.ccts.mqtt;

public enum MqttCctsCodes {
    NODE_DISCONNECT(700, "Node disconnect from broker"),
    NODE_CONNECT(701, "Node connect to broker"),
    WEB_SERVER_SEND_CONFIG(702, "Server send configuration to the last node connected"),
    WEB_SERVER_DISCONNECT(703, "Server disconnected from broker"),
    NODE_NOT_FOUND(704, "Node identifier can't be found"),
    VISIT_MESSAGE_FROM_NODE(705, "New visit message from a node"),
    WEB_SERVER_CONNECT(706, "Server connect to broker"),
    UNKNOWN(0, "Unknow code received");

    private final Integer code;
    private final String description;

    MqttCctsCodes(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MqttCctsCodes getByCode(Integer code) {
        for(MqttCctsCodes e : values()) {
            if(e.getCode().equals(code))
                return e;
        }
        return UNKNOWN;
    }
}
