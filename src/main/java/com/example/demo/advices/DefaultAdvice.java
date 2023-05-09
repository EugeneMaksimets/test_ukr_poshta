package com.example.demo.advices;

import com.example.demo.responce.Response;
import com.example.demo.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleException(BusinessException e) {
        Response response = new Response(e.getMessage(), e.getDebugMessage(), e.getHttpStatus());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable
            (HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response("Invalid Json", ex.getMessage(), status);
        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported
            (HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response("Method not supported", ex.getMessage(), status);
        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable
            (MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response("Missing path variable", ex.getMessage(), status);
        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter
            (MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response("Missing request parameter", ex.getMessage(), status);
        return new ResponseEntity<>(response, status);
    }
}
