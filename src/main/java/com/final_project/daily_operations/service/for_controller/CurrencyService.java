package com.final_project.daily_operations.service.for_controller;

import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }
}