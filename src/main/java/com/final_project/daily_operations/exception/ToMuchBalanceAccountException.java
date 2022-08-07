package com.final_project.daily_operations.exception;

public class ToMuchBalanceAccountException extends InvalidBalanceException {

    public ToMuchBalanceAccountException(String message) {
        super(message);
    }
}