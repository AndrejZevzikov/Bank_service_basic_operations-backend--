package com.final_project.daily_operations;

import com.final_project.daily_operations.model.*;
import com.final_project.daily_operations.preparedData.*;
import com.final_project.daily_operations.repostory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = {"com/final_project/daily_operations/*"})
public class DailyOperationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyOperationsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(final NewsRepository newsRepository, @Autowired NewsPreparedData newsPreparedData,
                                 final CustomerRepository customerRepository, @Autowired CustomerPreparedData customerPreparedData,
                                 final CurrencyRepository currencyRepository, @Autowired CurrencyPreparedData currencyPreparedData,
                                 final CurrencyRateRepository currencyRateRepository, @Autowired BalancesPreparedData balancesPreparedData,
                                 final BalanceRepository balanceRepository, @Autowired TransactionPreparedData transactionPreparedData,
                                 final TransactionRepository transactionRepository) {
        return args -> {
            List<Customer> customers = customerPreparedData.setUpCustomers();
            List<News> news = newsPreparedData.setUpNews();
            news.forEach(news1 -> news1.setCustomer(customers.get(0)));
            customers.get(0).setNews(news);
            customerRepository.saveAll(customers);
            currencyRepository.saveAll(currencyPreparedData.setUpCurrencies());
            currencyRateRepository.saveAll(List.of(
                    new CurrencyRate(
                            null,
                            LocalDate.of(2020, 2, 22),
                            1.05,
                            currencyRepository.findById(2L).get()),
                    new CurrencyRate(
                            null,
                            LocalDate.of(2022, 6, 23),
                            1.15,
                            currencyRepository.findById(2L).get())));

            List<Balance> balances = balancesPreparedData.setUpBalances();
            List<Transaction> transactions = transactionPreparedData.setUpTransactions();
            transactions.get(0).setCurrency(currencyRepository.findById(2L).get());
            transactions.get(1).setCurrency(currencyRepository.findById(1L).get());
            balances.get(0).setCustomer(customerRepository.findById(2L).get());
            balances.get(0).setCurrency(currencyRepository.findById(2L).get());
            balances.get(1).setCustomer(customerRepository.findById(2L).get());
            balances.get(1).setCurrency(currencyRepository.findById(1L).get());

            balanceRepository.saveAll(balancesPreparedData.setUpBalances());
            transactionRepository.saveAll(transactionPreparedData.setUpTransactions());
        };
    }
}
