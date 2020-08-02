package com.project.ccts.jwt;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration class for JWT, basic parameters.
 *
 */
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String issuer;
    private String secretKey;
    private String tokenPrefix;
    private Long tokenExpirationAfterMilliseconds;

    public JwtConfig() { }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Long getTokenExpirationAfterMilliseconds() {
        return tokenExpirationAfterMilliseconds;
    }

    public void setTokenExpirationAfterMilliseconds(Long tokenExpirationAfterMilliseconds) {
        this.tokenExpirationAfterMilliseconds = tokenExpirationAfterMilliseconds;
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}