package com.final_project.daily_operations.exceptionsHandling;

import com.final_project.daily_operations.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(TakenUsernameException.class)
    public ResponseEntity<String> handleTakenUsernameException(TakenUsernameException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(EmptyFieldsException.class)
    public ResponseEntity<String> handleEmptyFieldsException(EmptyFieldsException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(e.getMessage());
    }
    @ExceptionHandler(EmailDoesNotExistException.class)
    public ResponseEntity<String> handleEmailDoesNotExists(EmailDoesNotExistException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(UUIDExpiredOrDoesNotExistException.class)
    public ResponseEntity<String> handleUuidDoesNotExist(UUIDExpiredOrDoesNotExistException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<String> handleInvalidCurrency(InvalidCurrencyException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}