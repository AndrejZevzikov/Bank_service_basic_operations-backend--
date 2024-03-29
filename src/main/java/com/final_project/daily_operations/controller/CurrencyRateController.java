package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.ChartRatesDto;
import com.final_project.daily_operations.dto.CurrencyRateDto;
import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.service.modelService.CurrencyRateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/currency_rates")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class CurrencyRateController { //TODO charta
    private final CurrencyRateService currencyRateService;
    private final MapperDto mapperDto;

    @GetMapping
    public ResponseEntity<List<CurrencyRateDto>> getLastCurrencyRates() throws NoSuchObjectInDatabaseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapperDto.toCurrencyRateDtoList(currencyRateService.getLastCurrencyRate()));
    }

    @GetMapping("/update")
    public ResponseEntity<List<CurrencyRateDto>> updateLastCurrencyRates()
            throws IOException, NoSuchObjectInDatabaseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapperDto.toCurrencyRateDtoList(currencyRateService.updateLastCurrencyRates()));
    }

    @GetMapping("/chart_rates")
    public ResponseEntity<List<ChartRatesDto>> getChartRates() {
        return ResponseEntity.ok()
                .body(currencyRateService.getCurrencyRatesForLastMonth());
    }
}