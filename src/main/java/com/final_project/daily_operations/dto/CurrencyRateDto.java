package com.final_project.daily_operations.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CurrencyRateDto {
    private String code;
    private LocalDate date;
    private Double rate;
}