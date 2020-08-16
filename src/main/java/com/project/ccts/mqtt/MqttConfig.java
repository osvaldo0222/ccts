package com.project.ccts.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.mqtt.broker")
public class MqttConfig {
    private String url;
    private String username;
    private String password;
    private String clientId;
    private Boolean automaticReconnect;
    private Integer completionTimeout;
    private Integer qos;
    private String configTopic;
    private String nodesTopic;
    private String undefinedTopic;
    private String webServerStatus;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getAutomaticReconnect() {
        return automaticReconnect;
    }

    public void setAutomaticReconnect(Boolean automaticReconnect) {
        this.automaticReconnect = automaticReconnect;
    }

    public Integer getCompletionTimeout() {
        return completionTimeout;
    }

    public void setCompletionTimeout(Integer completionTimeout) {
        this.completionTimeout = completionTimeout;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getConfigTopic() {
        return configTopic;
    }

    public void setConfigTopic(String configTopic) {
        this.configTopic = configTopic;
    }

    public String getNodesTopic() {
        return nodesTopic;
    }

    public void setNodesTopic(String nodesTopic) {
        this.nodesTopic = nodesTopic;
    }

    public String getUndefinedTopic() {
        return undefinedTopic;
    }

    public void setUndefinedTopic(String undefinedTopic) {
        this.undefinedTopic = undefinedTopic;
    }

    public String getWebServerStatus() {
        return webServerStatus;
    }

    public void setWebServerStatus(String webServerStatus) {
        this.webServerStatus = webServerStatus;
    }
}
