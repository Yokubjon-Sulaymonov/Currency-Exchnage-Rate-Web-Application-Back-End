package com.example.exchangerate.module;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeRateModule {
    private String table;
    private String currency;
    private String code;
    private Rates[] rates;
}
