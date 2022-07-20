package com.final_project.daily_operations.helper;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CurrencyRateFetchingService {

    public String getCurrencyRatesXMLString(String endpoint) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

}
