package com.example.exchangerate.controller.exchangerate;

import com.example.exchangerate.module.ExchangeRateWithSpecifiedPeriod;
import com.example.exchangerate.service.exchangerate.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("exchangerate/rates")
public class ExchangeRateController {

    private static final HttpHeaders httpHeaders = new HttpHeaders();

    static {
        httpHeaders.add("Access-Control-Allow-Origin", "*");
    }

    private final ExchangeRateService service;

    @GetMapping("{currencyCode}")
    public ResponseEntity<Double> getExchangeRate(@PathVariable("currencyCode") String currencyCode)
            throws Exception {
        log.info("Attempting to retrieve exchange rate for currencyCode {}", currencyCode);
        Double exchangeRate = service.getExchangeRate(currencyCode);
        log.info("Successfully retrieved exchange rate for currencyCode {}", currencyCode);
        return ResponseEntity.ok().headers(httpHeaders).body(exchangeRate);
    }

    @GetMapping("{currencyCode}/{amount}")
    public ResponseEntity<Double> getExchangeRateWithAmount(@PathVariable("currencyCode") String currencyCode,
                                                            @PathVariable("amount") Double amount) throws Exception {
        log.info("Attempting to retrieve exchange rate for currencyCode {} in amount {}", currencyCode, amount);
        Double exchangeRate = service.getExchangeRateWithAmount(currencyCode, amount);
        log.info("Successfully retrieved exchange rate for currencyCode {} in amount {}", currencyCode, amount);
        return ResponseEntity.ok().headers(httpHeaders).body(exchangeRate);
    }

    @GetMapping("{currencyCode1}/to/{currencyCode2}/{amount}")
    public ResponseEntity<Double> getExchangeRateWithAmountForDifferentCurrency(
            @PathVariable("currencyCode1") String currencyCode1,
            @PathVariable("currencyCode2") String currencyCode2,
            @PathVariable("amount") Double amount,
            @RequestParam(value = "date", required = false) String date) throws Exception {
        log.info("Attempting to retrieve exchange rate for currencyCode {} to currencyCode {} in amount {}",
                currencyCode1, currencyCode2, amount);
        Double exchangeRate = service.getExchangeRateWithAmountForDifferentCurrency(currencyCode1,
                currencyCode2, amount, date);
        log.info("Successfully retrieved exchange rate for currencyCode {} to currencyCode {} in amount {}",
                currencyCode1, currencyCode2, amount);
        return ResponseEntity.ok().headers(httpHeaders).body(exchangeRate);
    }

    @GetMapping("{currencyCode}/month/{amountOfMonth}")
    public ResponseEntity<List<ExchangeRateWithSpecifiedPeriod>> getExchangeRateForSpecifiedPeriod(
            @PathVariable("currencyCode") String currencyCode,
            @PathVariable("amountOfMonth") Integer amountOfMonth) throws Exception {
        log.info("Attempting to retrieve exchange rate for currencyCode {} for last {} month",
                currencyCode, amountOfMonth);
        List<ExchangeRateWithSpecifiedPeriod> exchangeRates =
                service.getExchangeRateForSpecifiedPeriod(currencyCode, amountOfMonth);
        log.info("Successfully retrieved exchange rate for currencyCode {} for last {} month",
                currencyCode, amountOfMonth);
        return ResponseEntity.ok().headers(httpHeaders).body(exchangeRates);
    }
}
