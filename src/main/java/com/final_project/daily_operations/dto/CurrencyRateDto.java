package com.final_project.daily_operations.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class CurrencyRateDto {
    private String code;
    private LocalDate date;
    private Double rate;
}