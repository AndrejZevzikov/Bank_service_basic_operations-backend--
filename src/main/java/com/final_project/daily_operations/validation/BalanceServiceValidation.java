package com.final_project.daily_operations.validation;

import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.repostory.BalanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BalanceServiceValidation {
    public static final String ACCOUNT_WITH_THIS_CURRENCY_ALREADY_EXISTS = "Account with this currency already exists";//TODO properties file
    public static final String USER_REACHED_MAXIMUM_ALLOWED_ACCOUNTS_COUNT = "User reached maximum allowed accounts number";
    public static final int MAXIMUM_BALANCES_COUNT = 5;
    public static final String ACCOUNT_WITH_GIVEN_NUMBER_DOES_NOT_EXIST = "Account with given number does not exist";
    public static final String GIVEN_CURRENCY_AND_ACCOUNT_CURRENCY_DOES_NOT_MATCH = "Account number %s has currency %s and can't update balance with currency %s";
    private final BalanceRepository balanceRepository;

    public void isValidAddBalanceRequest(final Currency currency, final Customer customer) throws InvalidBalanceException {
        boolean isCurrencyExist = customer.getBalances().stream()
                .anyMatch(balance -> balance.getCurrency().getCode().equals(currency.getCode()));

        if (isCurrencyExist) {
            throw new DuplicateCurrencyAccountException(ACCOUNT_WITH_THIS_CURRENCY_ALREADY_EXISTS);
        }

        if (customer.getBalances().size() > MAXIMUM_BALANCES_COUNT) {
            throw new ToMuchBalanceAccountException(USER_REACHED_MAXIMUM_ALLOWED_ACCOUNTS_COUNT);
        }
    }

    public void isCurrencyValid(final Currency givenCurrency, final String givenAccountNumber)
            throws NoSuchObjectInDatabaseException, InvalidCurrencyException {
        Balance balanceToValidate = balanceRepository.findByAccountNumber(givenAccountNumber)
                .orElseThrow(() -> new NoSuchObjectInDatabaseException(ACCOUNT_WITH_GIVEN_NUMBER_DOES_NOT_EXIST));
        if (!balanceToValidate.getCurrency().equals(givenCurrency)){
            throw new InvalidCurrencyException(
                    String.format(GIVEN_CURRENCY_AND_ACCOUNT_CURRENCY_DOES_NOT_MATCH
                            ,givenAccountNumber,balanceToValidate.getCurrency().getName(),givenCurrency.getName()));
        }
    }
}