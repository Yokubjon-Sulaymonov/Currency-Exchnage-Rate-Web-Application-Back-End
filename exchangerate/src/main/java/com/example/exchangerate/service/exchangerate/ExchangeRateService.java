package com.example.exchangerate.service.exchangerate;

import com.example.exchangerate.module.ExchangeRateWithSpecifiedPeriod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExchangeRateService {

    /** This method is used to get exchange rate from currency code provided into PLN **/
    Double getExchangeRate(String currencyCode) throws Exception;

    /** This method is used to get exchange rate from currency code provided with amount into PLN **/
    Double getExchangeRateWithAmount(String currencyCode, Double amount) throws Exception;

    /** This method is used to get exchange rate from one currency code into another with amount **/
    Double getExchangeRateWithAmountForDifferentCurrency(String currencyCode1, String currencyCode2,
                                                         Double amount, String date) throws Exception;

    /** This method is used to get exchange rate from currency code for specified amount of month **/
    List<ExchangeRateWithSpecifiedPeriod> getExchangeRateForSpecifiedPeriod(String currencyCode,
                                                                            Integer amountOfMonth) throws Exception;
}
