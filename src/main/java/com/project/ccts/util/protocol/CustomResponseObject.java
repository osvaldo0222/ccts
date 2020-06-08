package com.project.ccts.util.protocol;

import java.time.LocalDateTime;

/**
 * Object to make a custom response when something occurred
 *
 */
public class CustomResponseObject {
    private final String timestamp;
    private final Integer httpStatus;
    private final String result;

    public CustomResponseObject(Integer httpStatus, String result) {
        this.timestamp = LocalDateTime.now().toString();
        this.httpStatus = httpStatus;
        this.result = result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return result;
    }
}
