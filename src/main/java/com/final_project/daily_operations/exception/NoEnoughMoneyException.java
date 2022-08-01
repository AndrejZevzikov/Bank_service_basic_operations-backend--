package com.final_project.daily_operations.exception;

public class NoEnoughMoneyException extends InvalidBalanceException{

    public NoEnoughMoneyException(String message) {
        super(message);
    }
}