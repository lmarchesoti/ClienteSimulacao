package dev.lmarchesoti.ClienteSimulacao.advice;

import dev.lmarchesoti.ClienteSimulacao.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFound(ResourceNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
