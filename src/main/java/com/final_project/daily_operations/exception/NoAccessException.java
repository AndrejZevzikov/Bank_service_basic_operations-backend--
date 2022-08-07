package com.final_project.daily_operations.exception;

public class NoAccessException extends InvalidBalanceException {

    public NoAccessException(String message) {
        super(message);
    }
}