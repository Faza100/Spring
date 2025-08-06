package com.indiedeveloper.finance_tracker_bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Log4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final OkHttpClient okHttpClient;

    private static final String CBR_API_URL = "https://www.cbr-xml-daily.ru/daily_json.js";

    private static final Map<String, String> CURRENCY_CODES = Map.of(
            "USD", "R01235",
            "EUR", "R01239",
            "BYN", "R01090B",
            "PLN", "R01565",
            "CNY", "R01375",
            "RUB", "RUB");

    public Map<String, BigDecimal> getCurrencyRates() {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("RUB", BigDecimal.ONE);

        try {
            Request request = new Request.Builder()
                    .url(CBR_API_URL)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);

                JSONObject jsonResponse = new JSONObject(response.body().string());
                JSONObject valutes = jsonResponse.getJSONObject("Valute");

                for (Map.Entry<String, String> entry : CURRENCY_CODES.entrySet()) {
                    JSONObject currency = valutes.getJSONObject(entry.getValue());
                    BigDecimal value = currency.getBigDecimal("Value");
                    BigDecimal nominal = currency.getBigDecimal("Nominal");
                    BigDecimal rate = value.divide(nominal, 6, RoundingMode.HALF_UP);
                    rates.put(entry.getKey(), rate);
                }
            }
        } catch (Exception e) {
            log.error("Error getting currency rates: " + e.getMessage());
        }

        return rates;
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        Map<String, BigDecimal> rates = getCurrencyRates();

        BigDecimal fromRate = rates.get(fromCurrency);
        BigDecimal toRate = rates.get(toCurrency);

        if (fromRate == null || toRate == null) {
            throw new IllegalArgumentException("Unsupported currency");
        }

        return amount.multiply(fromRate).divide(toRate, 2, RoundingMode.HALF_UP);
    }
}