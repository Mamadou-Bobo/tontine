package com.bobo.tontine.authentication.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mamadou Bobo on 30/10/2022
 * @Project Tontine
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        Map<String, Object> data = new HashMap<>();

        data.put("timestamp", new Date());
        data.put("code", httpStatus.value());
        data.put("status", httpStatus.name());
        data.put("message", exception.getMessage());

        response.setStatus(httpStatus.value());
        response
                .getOutputStream()
                .print(objectMapper.writeValueAsString(data));
    }
}
