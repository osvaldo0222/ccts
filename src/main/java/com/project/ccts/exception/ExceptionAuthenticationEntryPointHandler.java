package com.project.ccts.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ccts.util.ApiResponse;
import com.project.ccts.jwt.JwtUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Component
public class ExceptionAuthenticationEntryPointHandler implements org.springframework.security.web.AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ApiResponse responseApi = new ApiResponse(LocalDateTime.now().toString(), response.getStatus(), response.getHeader(JwtUtil.getApplicationHeader()), "", request.getServletPath());
        response.setContentType("application/json");
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, responseApi);
        out.flush();
    }
}
