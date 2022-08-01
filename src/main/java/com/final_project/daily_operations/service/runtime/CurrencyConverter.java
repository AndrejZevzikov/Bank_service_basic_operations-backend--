package com.final_project.daily_operations.service.runtime;

import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.CurrencyRate;
import com.final_project.daily_operations.repostory.CurrencyRateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CurrencyConverter {

    public static final String CURRENCY_RATES_DOES_NOT_EXIST = "Currency rates does not exist";
    private final CurrencyRateRepository currencyRateRepository;

    public Double convertBalanceByGivenDate(final LocalDate date, final Balance balance)
            throws NoSuchObjectInDatabaseException { //TODO testas
        if (balance.getCurrency().getId().equals(1L)) {
            return round(balance.getAmount());
        }
        Double rate = currencyRateRepository.findAll().stream()
                .filter(currencyRate -> currencyRate.getCurrency().getId().equals(balance.getCurrency().getId()))
                .filter(currencyRate -> currencyRate.getDate().isEqual(date) || currencyRate.getDate().isBefore(date))
                .max(Comparator.comparing(CurrencyRate::getDate))
                .map(CurrencyRate::getRate).orElseThrow(
                        () -> new NoSuchObjectInDatabaseException(CURRENCY_RATES_DOES_NOT_EXIST));
        return round(balance.getAmount() / rate);
    }

    public Double convertBalanceByGivenDate(final LocalDate date, final List<Balance> balances)
            throws NoSuchObjectInDatabaseException {
        Double result = 0.0; //TODO stream
        for (Balance balance : balances) {
            result += convertBalanceByGivenDate(date, balance);
        }
        return result;
    }

    public Double round(Double amount) {
        return (double) (Math.round(amount * 100)) / 100;
    }
}