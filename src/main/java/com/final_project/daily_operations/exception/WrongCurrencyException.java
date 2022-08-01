package com.final_project.daily_operations.exception;

public class WrongCurrencyException extends InvalidBalanceException {

    public WrongCurrencyException(String message) {
        super(message);
    }
}