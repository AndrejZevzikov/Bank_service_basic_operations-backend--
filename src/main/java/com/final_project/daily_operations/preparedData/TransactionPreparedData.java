package com.final_project.daily_operations.preparedData;

import com.final_project.daily_operations.model.Transaction;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.repostory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TransactionPreparedData {

    @Bean
    public List<Transaction> setUpTransactions() {
        Transaction transaction1 = Transaction.builder()
                .amount(150.2)
                .localDate(LocalDate.of(2022, 7, 15))
                .receiverAccountNumber("INV2202207151")
                .payerAccountNumber("Unknown")
                .amount(15.0)
                .build();

        Transaction transaction2 = Transaction.builder()
                .amount(150.2)
                .localDate(LocalDate.of(2022, 6, 15))
                .receiverAccountNumber("unknown")
                .payerAccountNumber("INV2202207152")
                .amount(25.0)
                .build();

        return List.of(transaction1, transaction2);


    }
}
