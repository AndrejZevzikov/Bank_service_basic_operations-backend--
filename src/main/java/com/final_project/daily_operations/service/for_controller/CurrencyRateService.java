package com.final_project.daily_operations.service.for_controller;

import com.final_project.daily_operations.model.CurrencyRate;
import com.final_project.daily_operations.repostory.CurrencyRateRepository;
import com.final_project.daily_operations.service.runtime.CurrencyRuntimeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyRateService {

    private CurrencyRateRepository currencyRateRepository;
    private CurrencyRuntimeService currencyRuntimeService;

    public List<CurrencyRate> getLastCurrencyRate(){//TODO validadcijos
        LocalDate lastUpdate = currencyRateRepository.getLastUpdateDate().get();
        return currencyRateRepository.getCurrencyRatesByGiveDate(lastUpdate).get();
    }

    public List<CurrencyRate> updateLastCurrencyRates() throws IOException {
        currencyRuntimeService.updateCurrentCurrencyRates();
        return getLastCurrencyRate();
    }
}
