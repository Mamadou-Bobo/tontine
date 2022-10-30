package com.bobo.tontine.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends IllegalArgumentException {
    public BadRequestException(String message) {
        super(message);
    }
}
