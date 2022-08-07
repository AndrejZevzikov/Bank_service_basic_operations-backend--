package com.final_project.daily_operations.mapper.mapperDto;

import com.final_project.daily_operations.dto.TransactionDto;
import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.model.Transaction;
import com.final_project.daily_operations.service.modelService.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperFromDto {

    private final CurrencyService currencyService;

    public Transaction fromTransactionDto(TransactionDto transactionDto) throws NoSuchObjectInDatabaseException {
        return Transaction.builder()
                .localDate(transactionDto.getLocalDate())
                .amount(transactionDto.getAmount())
                .currency(currencyService.getCurrencyByCode(transactionDto.getCurrencyCode()))
                .payerAccountNumber(transactionDto.getPayerAccountNumber())
                .receiverAccountNumber(transactionDto.getReceiverAccountNumber())
                .comment(transactionDto.getComment())
                .build();
    }
}