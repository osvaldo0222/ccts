package com.project.ccts;

import com.project.ccts.jwt.JwtConfig;
import com.project.ccts.mqtt.MqttConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({JwtConfig.class, MqttConfig.class})
@EnableScheduling
public class CctsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CctsApplication.class, args);
    }
}
