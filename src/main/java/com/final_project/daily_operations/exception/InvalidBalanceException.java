package com.final_project.daily_operations.exception;

public abstract class InvalidBalanceException extends Exception {
    public InvalidBalanceException(String message) {
        super(message);
    }
}