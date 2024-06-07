package com.harkesh.letterbox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUserRequest extends RuntimeException {
    public InvalidUserRequest(String message) {
        super(message);
    }
}


