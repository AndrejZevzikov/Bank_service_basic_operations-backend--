package com.final_project.daily_operations.mapper.mapperService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class CurrencyRateXMLMapper {

    public Map<String, Double> getCurrencyRatesMapFromXMLString(String xmlString) {

        Map<String, Double> currencyRates = new HashMap<>();

        Scanner scanner = new Scanner(xmlString); //TODO unmarshaller
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("<Ccy>") && !line.contains("EUR")) {
                String currencyCode = line
                        .replace("<Ccy>", "")
                        .replace("</Ccy>", "")
                        .replaceAll("\\s+", "");
                Double currencyRate = Double.valueOf(scanner.nextLine()
                        .replace("<Amt>", "")
                        .replace("</Amt>", ""));
                currencyRates.put(currencyCode, currencyRate);
            }
        }
        scanner.close();
        return currencyRates;
    }
}