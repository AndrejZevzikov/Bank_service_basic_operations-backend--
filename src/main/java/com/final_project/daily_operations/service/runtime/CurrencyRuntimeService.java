package com.final_project.daily_operations.service.runtime;

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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.final_project.daily_operations.constants.LithuaniaBankApi.LAST_CURRENCY_RATES_API;

@Component
@AllArgsConstructor
@Slf4j
public class CurrencyRuntimeService {
    private CurrencyRepository currencyRepository;
    private CurrencyRateRepository currencyRateRepository;
    private CurrencyRateXMLMapper currencyRateXMLMapper;
    private CurrencyRateFetchingService currencyRateFetchingService;

    public void updateCurrentCurrencyRates() throws IOException {
        log.info("Start updating currency rate");

        Map<String, Double> currencyRatesFromApi = currencyRateXMLMapper.getCurrencyRatesMapFromXMLString(
                currencyRateFetchingService.getCurrencyRatesXMLString(LAST_CURRENCY_RATES_API));
        List<Currency> currenciesInDB = currencyRepository.findAll();

        currenciesInDB.forEach(currency -> {
            if (currencyRatesFromApi.get(currency.getCode()) != null) {
                CurrencyRate cr = CurrencyRate.builder()
                        .currency(currency)
                        .date(LocalDate.now())
                        .rate(currencyRatesFromApi.get(currency.getCode()))
                        .build();
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
        });
        log.info("finished update currency rates");
    }
}
