package com.final_project.daily_operations.exceptionsHandling;

import com.final_project.daily_operations.exception.EmailDoesNotExistException;
import com.final_project.daily_operations.exception.TakenUsernameException;
import com.final_project.daily_operations.exception.EmptyFieldsException;
import com.final_project.daily_operations.exception.UUIDExpiredOrDoesNotExistException;
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
    @ExceptionHandler(EmailDoesNotExistException.class)
    public ResponseEntity handleEmailDoesNotExists(EmailDoesNotExistException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(UUIDExpiredOrDoesNotExistException.class)
    public ResponseEntity handleUuidDoesNotExist(UUIDExpiredOrDoesNotExistException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
