package com.bobo.tontine.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mamadou Bobo on 21/10/2022
 * @Project Tontine
 */

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceFoundException extends RuntimeException {
    public ResourceFoundException(String message) {
        super(message);
    }
}
