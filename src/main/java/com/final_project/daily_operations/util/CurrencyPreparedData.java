package com.final_project.daily_operations.util;

import com.final_project.daily_operations.model.Currency;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CurrencyPreparedData {

    public List<Currency> setUpCurrencies() {

        Currency eur = Currency.builder()
                .name("Euro")
                .code("EUR")
                .build();

        Currency usd = Currency.builder()
                .name("United States Dollar")
                .code("USD")
                .build();

        Currency chf = Currency.builder()
                .name("Switzerland frank")
                .code("CHF")
                .build();
        Currency cad = Currency.builder()
                .name("Canadian dollar")
                .code("CAD")
                .build();

        return List.of(eur, usd, chf, cad);
    }
}
