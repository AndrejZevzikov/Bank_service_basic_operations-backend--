package com.final_project.daily_operations.service.modelService;

import com.final_project.daily_operations.dto.ChartRatesDto;
import com.final_project.daily_operations.dto.DataPointsDto;
import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.model.CurrencyRate;
import com.final_project.daily_operations.repostory.CurrencyRateRepository;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.service.runtime.CurrencyRuntimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CurrencyRateService {

    public static final String EMPTY_DATA_IN_CURRENCY_RATES_TABLE = "Empty data in Currency rates table";
    public static final int MONTH = 31;
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyRuntimeService currencyRuntimeService;
    private final CurrencyRepository currencyRepository;

    public List<CurrencyRate> getLastCurrencyRate() throws NoSuchObjectInDatabaseException {
        LocalDate lastUpdate = currencyRateRepository.getLastUpdateDate().orElseThrow(
                () -> new NoSuchObjectInDatabaseException(EMPTY_DATA_IN_CURRENCY_RATES_TABLE));
        return currencyRateRepository.getCurrencyRatesByGiveDate(lastUpdate).orElseThrow(
                () -> new NoSuchObjectInDatabaseException(EMPTY_DATA_IN_CURRENCY_RATES_TABLE));
    }

    public List<CurrencyRate> updateLastCurrencyRates() throws IOException, NoSuchObjectInDatabaseException {
        currencyRuntimeService.updateCurrentCurrencyRates();
        return getLastCurrencyRate();
    }

    public List<ChartRatesDto> getCurrencyRatesForLastMonth() {
        log.info("Getting currency rates for last month");
        return currencyRepository.findAll().stream()
                .filter(currency -> !currency.getId().equals(1L))
                .map(this::mapCurrencyRateToChartRate)
                .collect(Collectors.toList());
    }

    private ChartRatesDto mapCurrencyRateToChartRate(Currency currency) {
        List<CurrencyRate> lastMonthCurrencyRates = currencyRateRepository.getCurrencyRatesAfterGiveDateAndCurrencyId(
                LocalDate.now().minusDays(MONTH), currency.getId());
        return ChartRatesDto.builder()
                .name(currency.getCode())
                .type("spline")
                .showInLegend(true)
                .dataPoints(mapDataPointsList(lastMonthCurrencyRates))
                .build();
    }

    private List<DataPointsDto> mapDataPointsList(List<CurrencyRate> rates) {
        List<DataPointsDto> dataPointsDto = rates.stream()
                .map(currencyRate -> DataPointsDto.builder()
                        .y(currencyRate.getRate())
                        .label(currencyRate.getDate().toString())
                        .build())
                .collect(Collectors.toList());

        for (int i = 0; i < dataPointsDto.size(); i++) {
            dataPointsDto.get(i).setX(i);
        }
        return dataPointsDto;
    }
}