package com.final_project.daily_operations.preparedData;

import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.repostory.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BalancesPreparedData {
;

    @Bean
    public List<Balance> setUpBalances() {
        Balance balance1 = Balance.builder()
                .amount(250.0)
                .accountNumber("INV2202207151")
                .build();
        Balance balance2 = Balance.builder()
                .amount(280.0)
                .accountNumber("INV2202207152")
                .build();

        return List.of(balance1, balance2);
    }
}
