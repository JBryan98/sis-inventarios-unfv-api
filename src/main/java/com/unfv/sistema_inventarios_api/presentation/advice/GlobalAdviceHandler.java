package com.unfv.sistema_inventarios_api.presentation.advice;

import com.unfv.sistema_inventarios_api.common.exception.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalAdviceHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest rq){
        return new ResponseEntity<>(ExceptionResponse.builder()
                .error("Entity Not Found")
                .message(ex.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    private ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex, WebRequest rq){
        return new ResponseEntity<>(ExceptionResponse.builder()
                .error("Duplicate key exception")
                .message(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest rq){
        return new ResponseEntity<>(ExceptionResponse.builder()
                .error("Bad credentials exception")
                .message(ex.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }
}