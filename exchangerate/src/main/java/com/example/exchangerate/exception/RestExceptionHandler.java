package com.example.exchangerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchCurrencyCodeSupportedException.class)
    public ResponseEntity<Error> nuSuchCurrencyCodeSupported(NoSuchCurrencyCodeSupportedException exception) {
        Error error = new Error(ExceptionType.NoSuchCurrencyCodeSupported, exception.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoDataForThisPeriodException.class)
    public ResponseEntity<Error> noDataForThisPeriod(NoDataForThisPeriodException exception) {
        Error error = new Error(ExceptionType.NoDataForThisDate, exception.getMessage());
        return ResponseEntity.status(500).body(error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CouldNotSendEmailException.class)
    public ResponseEntity<Error> couldNotSendEmailException(CouldNotSendEmailException exception) {
        Error error = new Error(ExceptionType.CouldNotSendEmail, exception.getMessage());
        return ResponseEntity.status(500).body(error);
    }
}
