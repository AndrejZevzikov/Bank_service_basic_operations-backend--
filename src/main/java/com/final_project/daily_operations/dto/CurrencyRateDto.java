package com.final_project.daily_operations.dto;

import com.final_project.daily_operations.model.CurrencyRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class CurrencyRateDto {
    private String code;
    private LocalDate date;
    private Double rate;

    public CurrencyRateDto getCurrencyRateDto(CurrencyRate currencyRate) {
        return new CurrencyRateDto(currencyRate.getCurrency().getCode(), currencyRate.getDate(), currencyRate.getRate());
    }

    public List<CurrencyRateDto> getListOfCurrencyRateDto(List<CurrencyRate> rates){
        return rates.stream()
                .map(this::getCurrencyRateDto)
                .collect(Collectors.toList());
    }
}
