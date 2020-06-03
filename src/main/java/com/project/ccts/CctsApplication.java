package com.project.ccts;

import com.project.ccts.jwt.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class CctsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CctsApplication.class, args);
    }

}
