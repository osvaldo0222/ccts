package com.project.ccts.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * Bean for making the JWT secret key.
 *
 */
@Configuration
public class JwtSecretKey {

    private JwtConfig jwtConfig;

    @Autowired
    public void setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Using the HMAC algorithm, it returns the key for JWT.
     *
     * @return SecretKey
     */
    @Bean
    public SecretKey SecretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }
}