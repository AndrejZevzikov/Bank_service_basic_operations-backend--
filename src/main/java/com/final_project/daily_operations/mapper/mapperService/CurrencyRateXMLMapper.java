package com.final_project.daily_operations.mapper.mapperService;

import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.model.CurrencyRate;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.service.modelService.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class CurrencyRateXMLMapper {
    private final CurrencyService currencyService;
    private final CurrencyRepository currencyRepository;

    public List<CurrencyRate> getCurrencyRatesMapFromXMLString(String xmlString, LocalDate date)
            throws NoSuchObjectInDatabaseException {

        List<CurrencyRate> currencyRates = new ArrayList<>();

        Scanner scanner = new Scanner(xmlString); //TODO unmarshaller
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("<Ccy>") && !line.contains("EUR")) {
                String currencyCode = line
                        .replace("<Ccy>", "")
                        .replace("</Ccy>", "")
                        .replaceAll("\\s+", "");
                if (currencyRepository.findByCode(currencyCode).isPresent()) {
                    Double currencyRate = Double.valueOf(scanner.nextLine()
                            .replace("<Amt>", "")
                            .replace("</Amt>", ""));
                    currencyRates.add(CurrencyRate.builder()
                            .rate(currencyRate)
                            .currency(currencyService.getCurrencyByCode(currencyCode))
                            .date(date)
                            .build());
                }
            }
        }
        scanner.close();
        log.info("returning currency rates list");
        return currencyRates;
    }
}