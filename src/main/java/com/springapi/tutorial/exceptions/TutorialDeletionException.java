package com.springapi.tutorial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TutorialDeletionException extends RuntimeException {
    public TutorialDeletionException(String message) {
        super(message);
    }
}
