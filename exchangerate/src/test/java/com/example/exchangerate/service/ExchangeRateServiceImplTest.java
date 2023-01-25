package com.example.exchangerate.service;

import com.example.exchangerate.service.exchangerate.ExchangeRateServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceImplTest {


    @Mock
    private ExchangeRateServiceImpl serviceMock;

    @Test
    public void shouldReturnCorrectExchangeRate() throws Exception {
        when(serviceMock.getExchangeRateFromAnyTable(anyString())).thenReturn(7.0D);
        when(serviceMock.getExchangeRate("USD")).thenCallRealMethod();
        assertEquals(serviceMock.getExchangeRate("USD").toString(), "7.0");
    }

    @Test
    public void shouldReturnCorrectExchangeRateWithAmount() throws Exception {
        when(serviceMock.getExchangeRate(anyString())).thenReturn(7.0D);
        when(serviceMock.getExchangeRateWithAmount(anyString(), anyDouble())).thenCallRealMethod();
        assertEquals(serviceMock.getExchangeRateWithAmount("USD", 3.0D).toString(), "21.0");
    }

    @Test
    public void shouldReturnCorrectExchangeRateWithAmountForDifferentCurrenciesWithDate() throws Exception {
        when(serviceMock.getExchangeRateFromAnyTableWithSpecifiedDate(anyString(), any())).thenReturn(7.0D);
        when(serviceMock.getExchangeRateWithAmountForDifferentCurrency("USD", "GBP",
                3.0D, Date.from(Instant.now()).toString())).thenCallRealMethod();
        assertEquals(serviceMock.getExchangeRateWithAmountForDifferentCurrency("USD", "GBP",
                3.0D, Date.from(Instant.now()).toString()), 3.0D);
    }

    @Test
    public void shouldReturnCorrectExchangeRateWithAmountForDifferentCurrenciesWithoutDate() throws Exception {
        when(serviceMock.getExchangeRate(anyString())).thenReturn(7.0D);
        when(serviceMock.getExchangeRateWithAmount(anyString(), anyDouble())).thenReturn(7.0D);
        when(serviceMock.getExchangeRateWithAmountForDifferentCurrency("USD", "GBP",
                3.0D, null)).thenCallRealMethod();
        assertEquals(serviceMock.getExchangeRateWithAmountForDifferentCurrency("USD", "GBP",
                3.0D, null), 1.0D);
    }
}




