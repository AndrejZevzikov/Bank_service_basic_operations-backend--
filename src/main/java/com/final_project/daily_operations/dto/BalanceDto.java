package com.final_project.daily_operations.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BalanceDto {
    private String accountNumber;
    private Double amount;
    private String currencyCode;
}