package com.recrutTask.swift_api.exceptions;

public class BankNotFoundException extends RuntimeException{
    public BankNotFoundException(String message) {
        super(message);
    }
}
