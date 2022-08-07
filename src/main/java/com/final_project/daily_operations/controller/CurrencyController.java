package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.CurrencyDto;
import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.service.modelService.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        return ResponseEntity
                .ok()
                .body(mapperDto.toCurrencyDtoList(currencyService.getAllCurrencies()));
    }

    @GetMapping("/code/{id}")
    public ResponseEntity<String> getCurrencyCodeById(@PathVariable(name = "id") Long id)
            throws NoSuchObjectInDatabaseException {
        return ResponseEntity.ok()
                .body(currencyService.getCurrencyById(id).getCode());
    }

    @GetMapping("/id/{code}")
    public ResponseEntity<Long> getCurrencyIdByCode(@PathVariable(name = "code") String code)
            throws NoSuchObjectInDatabaseException {
        System.out.println("getting code");
        return ResponseEntity.ok()
                .body(currencyService.getCurrencyIdByCode(code));
    }
}