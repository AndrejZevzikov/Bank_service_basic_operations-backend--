package com.final_project.daily_operations.exception;

public class DuplicateCurrencyAccountException extends InvalidBalanceException {

    public DuplicateCurrencyAccountException(String message) {
        super(message);
    }
}
