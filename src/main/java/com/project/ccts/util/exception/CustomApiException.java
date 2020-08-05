package com.project.ccts.util.exception;

/**
 * Custom class for CCTS specific API
 *
 */
public class CustomApiException extends Exception {
    private Integer code;

    /**
     * Generated the exception
     *
     * @param message message for the code
     * @param code code of the exception
     */
    public CustomApiException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
