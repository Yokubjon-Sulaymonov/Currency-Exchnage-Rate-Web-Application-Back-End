package com.example.exchangerate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {

    private ExceptionType type;
    private String message;
}
