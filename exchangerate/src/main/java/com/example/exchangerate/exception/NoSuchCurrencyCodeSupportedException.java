package com.example.exchangerate.exception;

public class NoSuchCurrencyCodeSupportedException extends RuntimeException {

    public NoSuchCurrencyCodeSupportedException(String message) {
        super(message);
    }
}
