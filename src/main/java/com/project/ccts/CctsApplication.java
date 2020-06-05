package com.project.ccts;

import com.project.ccts.jwt.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class CctsApplication {

    public static void main(String[] args) {
        //TODO: check if the authorization request have a notificationToken
        //TODO: make a better way to add default roles and privileges
        SpringApplication.run(CctsApplication.class, args);
    }

}
