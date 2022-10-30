package com.bobo.tontine.authentication.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mamadou Bobo on 30/10/2022
 * @Project Tontine
 */
public class httpStatusHandler {
    public static void httpHandler(HttpServletResponse response, HttpStatus httpStatus, String message, ObjectMapper objectMapper) throws IOException {
        Map<String, Object> data = new HashMap<>();

        data.put("timestamp",new Date());
        data.put("code", httpStatus.value());
        data.put("status", httpStatus.name());
        data.put("message", message);

        response.setStatus(httpStatus.value());
        response
                .getOutputStream()
                .print(objectMapper.writeValueAsString(data));
    }
}
