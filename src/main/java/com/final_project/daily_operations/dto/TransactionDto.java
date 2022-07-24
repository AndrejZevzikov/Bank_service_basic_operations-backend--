package com.final_project.daily_operations.dto;

import com.final_project.daily_operations.model.Currency;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class TransactionDto {

    private String payerAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
    private String currencyCode;
    private LocalDate localDate;
}
