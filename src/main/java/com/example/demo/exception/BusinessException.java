package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BusinessException extends Exception {

    private String debugMessage;
    private HttpStatus httpStatus;

    public BusinessException(String message, String debugMessage, HttpStatus httpStatus) {
        super(message);
        this.debugMessage = debugMessage;
        this.httpStatus = httpStatus;
    }
}
