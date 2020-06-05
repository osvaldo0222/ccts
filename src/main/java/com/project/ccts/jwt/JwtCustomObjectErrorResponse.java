package com.project.ccts.jwt;

import java.time.LocalDateTime;

/**
 * Object to make a custom response when error occurred during authentication and authorization
 *
 */
public class JwtCustomObjectErrorResponse {
    private final String timestamp;
    private final Integer httpStatus;
    private final String message;

    public JwtCustomObjectErrorResponse(Integer httpStatus, String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
