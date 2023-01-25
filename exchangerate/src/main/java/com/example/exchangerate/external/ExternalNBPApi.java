package com.example.exchangerate.external;

import com.example.exchangerate.module.ExchangeRateModule;
import com.example.exchangerate.module.Rates;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ExternalNBPApi {

    public ExchangeRateModule getExchangeRateFromTableA(String currencyCode) throws Exception {
        HttpRequest request = buildRequest("A", currencyCode);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObject();
    }

    public ExchangeRateModule getExchangeRateFromTableB(String currencyCode) throws Exception {
        HttpRequest request = buildRequest("B", currencyCode);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObject();
    }

    public ExchangeRateModule getExchangeRateFromTableC(String currencyCode) throws Exception {
        HttpRequest request = buildRequest("C", currencyCode);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObject();
    }

    public ExchangeRateModule getExchangeRateFromTableAForSpecifiedPeriod(String currencyCode,
                                                                    String startDate,
                                                                    String endDate) throws Exception {
        HttpRequest request = buildRequestForSpecifiedPeriod("A", currencyCode, startDate, endDate);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObjectForSpecifiedPeriod();
    }

    public ExchangeRateModule getExchangeRateFromTableBForSpecifiedPeriod(String currencyCode,
                                                                          String startDate,
                                                                          String endDate) throws Exception {
        HttpRequest request = buildRequestForSpecifiedPeriod("B", currencyCode, startDate, endDate);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObject();
    }

    public ExchangeRateModule getExchangeRateFromTableCForSpecifiedPeriod(String currencyCode,
                                                                          String startDate,
                                                                          String endDate) throws Exception {
        HttpRequest request = buildRequestForSpecifiedPeriod("C", currencyCode, startDate, endDate);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObject();
    }

    public ExchangeRateModule getExchangeRateFromTableAForSpecifiedDate(String currencyCode,
                                                                        String date) throws Exception {
        HttpRequest request = buildRequestForSpecifiedDate("A", currencyCode, date);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObject();
    }

    public ExchangeRateModule getExchangeRateFromTableBForSpecifiedDate(String currencyCode,
                                                                        String date) throws Exception {
        HttpRequest request = buildRequestForSpecifiedDate("B", currencyCode, date);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObject();
    }

    public ExchangeRateModule getExchangeRateFromTableCForSpecifiedDate(String currencyCode,
                                                                        String date) throws Exception {
        HttpRequest request = buildRequestForSpecifiedDate("C", currencyCode, date);

        if(isValidResponse(getResponse(request))) {
            return mapJsonToObject(getResponse(request));
        }
        return generateEmptyObject();
    }

    private HttpRequest buildRequest(String table, String currencyCode) throws URISyntaxException {
        String exchangeRateApi = "https://api.nbp.pl/api/exchangerates/rates/%s/%s/";
        return HttpRequest.newBuilder()
                          .uri(new URI(String.format(exchangeRateApi, table, currencyCode)))
                          .build();
    }

    private HttpRequest buildRequestForSpecifiedPeriod(String table, String currencyCode,
                                                 String startDate, String endDate) throws URISyntaxException {
        String exchangeRateApi = "https://api.nbp.pl/api/exchangerates/rates/%s/%s/%s/%s";
        return HttpRequest.newBuilder()
                .uri(new URI(String.format(exchangeRateApi, table, currencyCode, startDate, endDate)))
                .build();
    }

    private HttpRequest buildRequestForSpecifiedDate(String table, String currencyCode, String date) throws URISyntaxException {
        String exchangeRateApi = "https://api.nbp.pl/api/exchangerates/rates/%s/%s/%s";
        return HttpRequest.newBuilder()
                .uri(new URI(String.format(exchangeRateApi, table, currencyCode, date, date)))
                .build();
    }

    private Boolean isValidResponse(HttpResponse<String> response) {
        return response.statusCode() == 200;
    }

    private HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private ExchangeRateModule generateEmptyObject() {
        return new ExchangeRateModule(null, null, null, new Rates[]{new Rates()});
    }

    private ExchangeRateModule generateEmptyObjectForSpecifiedPeriod() {
        return new ExchangeRateModule(null, null, null, null);
    }

    private ExchangeRateModule mapJsonToObject(HttpResponse<String> response) {
        return new Gson().fromJson(response.body(), ExchangeRateModule.class);
    }
}
