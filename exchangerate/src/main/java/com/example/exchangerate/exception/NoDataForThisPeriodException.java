package com.example.exchangerate.exception;

public class NoDataForThisPeriodException extends RuntimeException {

    public NoDataForThisPeriodException(String message) {
        super(message);
    }
}
