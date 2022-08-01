package com.final_project.daily_operations.validation;

import com.final_project.daily_operations.exception.DuplicateCurrencyAccountException;
import com.final_project.daily_operations.exception.InvalidBalanceException;
import com.final_project.daily_operations.exception.ToMuchBalanceAccountException;
import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BalanceServiceValidation {

    public static final String ACCOUNT_WITH_THIS_CURRENCY_ALREADY_EXISTS = "Account with this currency already exists";//TODO properties file
    public static final String USER_REACHED_MAXIMUM_ALLOWED_ACCOUNTS_COUNT = "User reached maximum allowed accounts number";
    public static final int MAXIMUM_BALANCES_COUNT = 5;

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
}