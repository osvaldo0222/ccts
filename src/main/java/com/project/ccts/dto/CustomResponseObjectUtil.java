package com.project.ccts.dto;

import org.springframework.http.HttpStatus;

public class CustomResponseObjectUtil {

    /**
     * For using HTTP status code
     *
     * @param status
     * @param message
     * @return
     */
    public static CustomResponseObjectDTO createResponse(HttpStatus status, Object message) {
        return new CustomResponseObjectDTO(status.value(), status.getReasonPhrase(), message);
    }

    /**
     * For usign custom responses code and reason
     *
     * @param code
     * @param reason
     * @param message
     * @return
     */
    public static CustomResponseObjectDTO createResponse(Integer code, String reason, Object message) {
        return new CustomResponseObjectDTO(code, reason, message);
    }
}
