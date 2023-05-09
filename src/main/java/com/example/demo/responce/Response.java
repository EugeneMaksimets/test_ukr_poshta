package com.example.demo.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class Response {

    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String debugMessage;
    private HttpStatus httpStatus;

    public Response(String message, String debugMessage, HttpStatus httpStatus) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.httpStatus = httpStatus;
    }
}