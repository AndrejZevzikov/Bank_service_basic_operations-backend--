package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.CurrencyDto;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.service.for_controller.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/currency")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class CurrencyController {

    private final CurrencyService currencyService;
    private final MapperDto mapperDto;

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies(){
        return ResponseEntity
                .ok()
                .body(mapperDto.toCurrencyDtoList(currencyService.getAllCurrencies()));
    }
}
