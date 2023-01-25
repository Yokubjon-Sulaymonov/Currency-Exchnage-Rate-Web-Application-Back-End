package com.example.exchangerate.controller;

import com.example.exchangerate.module.ExchangeRateWithSpecifiedPeriod;
import com.example.exchangerate.service.exchangerate.ExchangeRateServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.example.exchangerate.exception.ExceptionType.NoSuchCurrencyCodeSupported;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExchangeRateControllerTest {

    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext applicationContext;

    private static final ExchangeRateServiceImpl SERVICE = new ExchangeRateServiceImpl();
    private static final String CURRENCY_CODE1 = "CAD";
    private static final String CURRENCY_CODE2 = "GBP";
    private static final String UNSUPPORTED_CURRENCY_CODE = "ABCD";
    private static final Double AMOUNT_OF_MONEY = 5D;
    private static final String CONVERSION_DATE = "2022-11-15";
    private static final Integer AMOUNT_OF_MONTH = 2;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(applicationContext).build();
    }

    @Test
    public void shouldReturnResultWhenGotExchangeRate() throws Exception {
        Double expectedResult = SERVICE.getExchangeRate(CURRENCY_CODE1);
        this.mockMvc.perform(get("/exchangerate/rates/{currencyCode}", CURRENCY_CODE1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedResult));
    }

    @Test
    public void shouldReturnExceptionWhenFailedToGetExchangeRate() throws Exception {
        this.mockMvc.perform(get("/exchangerate/rates/{currencyCode}", UNSUPPORTED_CURRENCY_CODE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value(NoSuchCurrencyCodeSupported.toString()))
                .andExpect(jsonPath("$.message")
                        .value(UNSUPPORTED_CURRENCY_CODE + " is not supported"));
    }

    @Test
    public void shouldReturnResultWhenGotExchangeRateWithAmount() throws Exception {
        Double expectedResult = SERVICE.getExchangeRateWithAmount(CURRENCY_CODE1, AMOUNT_OF_MONEY);
        this.mockMvc.perform(get("/exchangerate/rates/{currencyCode}/{amount}",
                        CURRENCY_CODE1, AMOUNT_OF_MONEY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedResult));
    }

    @Test
    public void shouldReturnExceptionWhenFailedToGetExchangeRateWithAmount() throws Exception {
        this.mockMvc.perform(get("/exchangerate/rates/{currencyCode}/{amount}",
                        UNSUPPORTED_CURRENCY_CODE, AMOUNT_OF_MONTH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value(NoSuchCurrencyCodeSupported.toString()))
                .andExpect(jsonPath("$.message")
                        .value(UNSUPPORTED_CURRENCY_CODE + " is not supported"));
    }

    @Test
    public void shouldReturnResultWhenGotExchangeRateWithAmountForDifferentCurrenciesWithoutDate() throws Exception {
        Double expectedResult = SERVICE.getExchangeRateWithAmountForDifferentCurrency(
                CURRENCY_CODE1, CURRENCY_CODE2, AMOUNT_OF_MONEY, null);
        this.mockMvc.perform(get("/exchangerate/rates/{currencyCode1}/to/{currencyCode2}/{amount}",
                        CURRENCY_CODE1, CURRENCY_CODE2, AMOUNT_OF_MONEY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedResult));
    }

    @Test
    public void shouldReturnResultWhenGotExchangeRateWithAmountForDifferentCurrenciesWithDate() throws Exception {
        Double expectedResult = SERVICE.getExchangeRateWithAmountForDifferentCurrency(
                CURRENCY_CODE1, CURRENCY_CODE2, AMOUNT_OF_MONEY, CONVERSION_DATE);
        this.mockMvc.perform(get("/exchangerate/rates/{currencyCode1}/to/{currencyCode2}/{amount}",
                        CURRENCY_CODE1, CURRENCY_CODE2, AMOUNT_OF_MONEY)
                        .queryParam("date", CONVERSION_DATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedResult));
    }

    @Test
    public void shouldReturnListOfResultsWhenGotExchangeRateForSpecifiedPeriod() throws Exception {
        List<ExchangeRateWithSpecifiedPeriod> listOfResults =
                SERVICE.getExchangeRateForSpecifiedPeriod(CURRENCY_CODE1, AMOUNT_OF_MONTH);

        this.mockMvc.perform(get("/exchangerate/rates/{currencyCode}/month/{amountOfMonth}",
                        CURRENCY_CODE1, AMOUNT_OF_MONTH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[:1].exchangeRate").value(listOfResults.get(0).getExchangeRate()))
                .andExpect(jsonPath("$.[-1:].exchangeRate")
                        .value(listOfResults.get(listOfResults.size() - 1).getExchangeRate()));
    }
}
