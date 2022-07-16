package com.final_project.daily_operations.exceptionsHandling;

import com.final_project.daily_operations.exception.TakenUsernameException;
import com.final_project.daily_operations.exception.EmptyFieldsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(TakenUsernameException.class)
    public ResponseEntity handleTakenUsernameException(TakenUsernameException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(EmptyFieldsException.class)
    public ResponseEntity handleEmptyFieldsException(EmptyFieldsException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(e.getMessage());
    }
}
