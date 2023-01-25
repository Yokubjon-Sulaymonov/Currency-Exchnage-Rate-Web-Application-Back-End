package com.example.exchangerate.service.exchangerate;

import com.example.exchangerate.exception.NoSuchCurrencyCodeSupportedException;
import com.example.exchangerate.external.ExternalNBPApi;
import com.example.exchangerate.module.ExchangeRateWithSpecifiedPeriod;
import com.example.exchangerate.module.Rates;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Component
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExternalNBPApi externalNBPApi = new ExternalNBPApi();

    @Override
    public Double getExchangeRate(String currencyCode) throws Exception {
        return getExchangeRateFromAnyTable(currencyCode);
    }

    @Override
    public Double getExchangeRateWithAmount(String currencyCode, Double amount) throws Exception {
        return getExchangeRate(currencyCode) * amount;
    }

    @Override
    public Double getExchangeRateWithAmountForDifferentCurrency(String currencyCode1,
                                                                String currencyCode2,
                                                                Double amount,
                                                                String date) throws Exception {
        if (date == null) {
            return getExchangeRateWithAmount(currencyCode1, amount) / getExchangeRate(currencyCode2);
        }

        return getExchangeRateFromAnyTableWithSpecifiedDate(currencyCode1, date) * amount /
                getExchangeRateFromAnyTableWithSpecifiedDate(currencyCode2, date);
    }

    @Override
    public List<ExchangeRateWithSpecifiedPeriod> getExchangeRateForSpecifiedPeriod(
            String currencyCode, Integer amountOfMonth) throws Exception {
        List<ExchangeRateWithSpecifiedPeriod> exchangeRates = new LinkedList<>();

        amountOfMonth = Math.abs(amountOfMonth);
        String startDate = LocalDate.now().minusMonths(amountOfMonth).toString();
        String endDate = LocalDate.now().toString();

        Rates[] rateFromTableA =
                externalNBPApi.getExchangeRateFromTableAForSpecifiedPeriod(currencyCode, startDate, endDate).getRates();
        Rates[] rateFromTableB =
                externalNBPApi.getExchangeRateFromTableBForSpecifiedPeriod(currencyCode, startDate, endDate).getRates();
        Rates[] rateFromTableC =
                externalNBPApi.getExchangeRateFromTableCForSpecifiedPeriod(currencyCode, startDate, endDate).getRates();

        if (currencyCode.equals("PLN")) {
            exchangeRates.add(new ExchangeRateWithSpecifiedPeriod("null", 1.0D));
            exchangeRates.add(new ExchangeRateWithSpecifiedPeriod("null", 1.0D));
            exchangeRates.add(new ExchangeRateWithSpecifiedPeriod("null", 1.0D));
            exchangeRates.add(new ExchangeRateWithSpecifiedPeriod("null", 1.0D));
            exchangeRates.add(new ExchangeRateWithSpecifiedPeriod("null", 1.0D));
            exchangeRates.add(new ExchangeRateWithSpecifiedPeriod("null", 1.0D));
        }

        if (rateFromTableA != null) {
            for (Rates rates : rateFromTableA) {
                exchangeRates.add(new ExchangeRateWithSpecifiedPeriod(rates.getEffectiveDate(), rates.getMid()));
            }
        } else if (rateFromTableB != null) {
            for (Rates rates : rateFromTableB) {
                exchangeRates.add(new ExchangeRateWithSpecifiedPeriod(rates.getEffectiveDate(), rates.getMid()));
            }
        } else if (rateFromTableC != null) {
            for (Rates rates : rateFromTableC) {
                exchangeRates.add(new ExchangeRateWithSpecifiedPeriod(rates.getEffectiveDate(), rates.getMid()));
            }
        }
        return exchangeRates;
    }

    public Double getExchangeRateFromAnyTable(String currencyCode) throws Exception {
        if (currencyCode.equals("PLN")) {
            return 1.0;
        }

        if (externalNBPApi.getExchangeRateFromTableA(currencyCode).getRates()[0].getMid() == null) {
            if (externalNBPApi.getExchangeRateFromTableB(currencyCode).getRates()[0].getMid() == null) {
                if (externalNBPApi.getExchangeRateFromTableC(currencyCode).getRates()[0].getMid() == null) {
                    throw new NoSuchCurrencyCodeSupportedException(currencyCode + " is not supported");
                }
                return externalNBPApi.getExchangeRateFromTableC(currencyCode).getRates()[0].getMid();
            }
            return externalNBPApi.getExchangeRateFromTableB(currencyCode).getRates()[0].getMid();
        }
        return new ExternalNBPApi().getExchangeRateFromTableA(currencyCode).getRates()[0].getMid();
    }

    public Double getExchangeRateFromAnyTableWithSpecifiedDate(String currencyCode, String date) throws Exception {
        if (currencyCode.equals("PLN")) {
            return 1.0;
        }

        if (externalNBPApi.getExchangeRateFromTableAForSpecifiedDate(currencyCode, date).getRates()[0].getMid() == null) {
            if (externalNBPApi.getExchangeRateFromTableBForSpecifiedDate(currencyCode, date).getRates()[0].getMid() == null) {
                if (externalNBPApi.getExchangeRateFromTableCForSpecifiedDate(currencyCode, date).getRates()[0].getMid() == null) {
                    throw new NoSuchCurrencyCodeSupportedException(currencyCode + " is not supported");
                }
                return externalNBPApi.getExchangeRateFromTableCForSpecifiedDate(currencyCode, date).getRates()[0].getMid();
            }
            return externalNBPApi.getExchangeRateFromTableBForSpecifiedDate(currencyCode, date).getRates()[0].getMid();
        }
        return new ExternalNBPApi().getExchangeRateFromTableAForSpecifiedDate(currencyCode, date).getRates()[0].getMid();
    }
}
