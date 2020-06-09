package com.project.ccts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ccts.dto.CustomResponseObjectDTO;
import com.project.ccts.dto.CustomResponseObjectUtil;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Changing Spring's default object to Custom Response Object
 *
 */
@Configuration
public class JsonApiConfig extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        final HttpStatus status = HttpStatus.valueOf(Integer.parseInt(errorAttributes.get("status").toString()));
        return new ObjectMapper().convertValue(
                CustomResponseObjectUtil.createResponse(status, status.getReasonPhrase()),
                Map.class
        );
    }
}
