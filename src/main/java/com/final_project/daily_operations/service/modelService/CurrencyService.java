package com.final_project.daily_operations.service.modelService;

import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CurrencyService {

    public static final String CURRENCY_DO_NOT_EXIST = "In database currency with code %s doesn't exist";
    public static final String CURRENCY_WITH_GIVEN_ID_DOES_NOT_EXIST = "Currency with id %d does not exist";
    private final CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }

    public Currency getCurrencyById(final Long id) throws NoSuchObjectInDatabaseException {
        return currencyRepository.findById(id).orElseThrow(
                () -> new NoSuchObjectInDatabaseException(String.format(CURRENCY_WITH_GIVEN_ID_DOES_NOT_EXIST,id)));
    }

    public Currency getCurrencyByCode(final String currencyCode) throws NoSuchObjectInDatabaseException {
        return currencyRepository.findByCode(currencyCode).orElseThrow(
                () -> new NoSuchObjectInDatabaseException(String.format(CURRENCY_DO_NOT_EXIST,currencyCode)));
    }
}