package com.project.ccts.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This is invoked when user tries to access a secured api resource without the required authorities
 *
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        JwtHandlerUtil.prepareResponse(response, HttpServletResponse.SC_FORBIDDEN, JwtHandlerUtil.getFORBIDDEN());
    }
}
