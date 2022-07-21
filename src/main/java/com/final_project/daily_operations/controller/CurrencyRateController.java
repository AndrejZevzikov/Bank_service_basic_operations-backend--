package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.CurrencyRateDto;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.service.for_controller.CurrencyRateService;
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
public class CurrencyRateController {
    private CurrencyRateService currencyRateService;
    private MapperDto mapperDto;

    @GetMapping
    public ResponseEntity<List<CurrencyRateDto>> getLastCurrencyRates(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapperDto.toCurrencyRateDtoList(currencyRateService.getLastCurrencyRate()));
    }

    @GetMapping("/update")
    public ResponseEntity<List<CurrencyRateDto>> updateLastCurrencyRates() throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapperDto.toCurrencyRateDtoList(currencyRateService.updateLastCurrencyRates()));
    }
}
