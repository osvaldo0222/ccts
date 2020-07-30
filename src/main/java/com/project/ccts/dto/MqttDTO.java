package com.project.ccts.dto;

public class MqttDTO {
    private Integer code;
    private String nodeIdentifier;
    private Object data;

    public MqttDTO(Integer code, String nodeIdentifier, Object data) {
        this.code = code;
        this.nodeIdentifier = nodeIdentifier;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MqttDTO{" +
                "code=" + code +
                ", nodeIdentifier='" + nodeIdentifier + '\'' +
                ", data=" + data +
                '}';
    }
}
