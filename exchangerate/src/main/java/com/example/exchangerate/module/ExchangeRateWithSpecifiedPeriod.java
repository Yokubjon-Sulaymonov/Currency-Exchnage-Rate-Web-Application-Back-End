package com.example.exchangerate.module;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeRateWithSpecifiedPeriod {

    private String date;
    private Double exchangeRate;
}
