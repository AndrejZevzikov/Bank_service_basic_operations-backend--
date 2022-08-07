package com.final_project.daily_operations.helper;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CurrencyRateFetchingService {

    public String getCurrencyRatesXMLString(String endpoint) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public String getCurrencyRatesXMLStringByDate(String endpoint, LocalDate date) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String api = String.format(endpoint+date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Request request = new Request.Builder()
                .url(api)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

}
