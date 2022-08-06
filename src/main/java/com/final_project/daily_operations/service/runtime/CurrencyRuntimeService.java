package com.final_project.daily_operations.service.runtime;

import com.final_project.daily_operations.constants.LithuaniaBankApi;
import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.helper.CurrencyRateFetchingService;
import com.final_project.daily_operations.mapper.mapperService.CurrencyRateXMLMapper;
import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.model.CurrencyRate;
import com.final_project.daily_operations.repostory.CurrencyRateRepository;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.final_project.daily_operations.constants.LithuaniaBankApi.CURRENCY_RATES_BY_GIVEN_DATE_API;
import static com.final_project.daily_operations.constants.LithuaniaBankApi.LAST_CURRENCY_RATES_API;

@Component
@AllArgsConstructor
@Slf4j
public class CurrencyRuntimeService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyRateXMLMapper currencyRateXMLMapper;
    private final CurrencyRateFetchingService currencyRateFetchingService;

    public void updateCurrentCurrencyRates() throws IOException, NoSuchObjectInDatabaseException {
        log.info("Start updating currency rate");
        String currencyRateString = currencyRateFetchingService.getCurrencyRatesXMLString(LAST_CURRENCY_RATES_API);
        List<CurrencyRate> currencyRatesFromApi = currencyRateXMLMapper.getCurrencyRatesMapFromXMLString(
                currencyRateString, LocalDate.now());
        log.info("Got currency from api");
        List<Currency> currenciesInDB = currencyRepository.findAll();
        for (Currency currency : currenciesInDB) {
            CurrencyRate cr = currencyRatesFromApi.stream()
                    .filter(currencyRate -> currencyRate.getCurrency().getCode().equals(currency.getCode()))
                    .findAny().orElse(null);
            System.out.println(cr);
            if (cr == null) {
                continue;
            }
            Optional<CurrencyRate> possibleDuplicate = currencyRateRepository
                    .getCurrencyRateByDateAndCurrencyId(cr.getDate(), cr.getCurrency());
            if (possibleDuplicate.isEmpty()) {
                currencyRateRepository.save(cr);
                log.info("Saving new Currency rate");
            } else {
                possibleDuplicate.get().setRate(cr.getRate());
                currencyRateRepository.save(possibleDuplicate.get());
                log.info("Updating existing currency rate");
            }
        }
        log.info("finished update currency rates");
    }

    public void updateCurrencyRatesByGivenDate(LocalDate startDate, LocalDate endDate) throws IOException, NoSuchObjectInDatabaseException {

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            String currencyRateString = currencyRateFetchingService.getCurrencyRatesXMLStringByDate(
                    CURRENCY_RATES_BY_GIVEN_DATE_API, date);
            List<CurrencyRate> currencyRatesFromApi = currencyRateXMLMapper.getCurrencyRatesMapFromXMLString(
                    currencyRateString, date);
            List<Currency> currenciesInDB = currencyRepository.findAll();
            for (Currency currency : currenciesInDB) {
                CurrencyRate cr = currencyRatesFromApi.stream()
                        .filter(currencyRate -> currencyRate.getCurrency().getCode().equals(currency.getCode()))
                        .findAny().orElse(null);
                if (cr == null) {
                    continue;
                }
                Optional<CurrencyRate> possibleDuplicate = currencyRateRepository
                        .getCurrencyRateByDateAndCurrencyId(cr.getDate(), cr.getCurrency());
                if (possibleDuplicate.isEmpty()) {
                    currencyRateRepository.save(cr);
                    log.info("Saving new Currency rate");
                } else {
                    possibleDuplicate.get().setRate(cr.getRate());
                    currencyRateRepository.save(possibleDuplicate.get());
                }
            }
        }
    }
}