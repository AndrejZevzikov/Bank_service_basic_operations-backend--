package com.final_project.daily_operations.validation;

import com.final_project.daily_operations.dto.BalanceDto;
import com.final_project.daily_operations.exception.DuplicateCurrencyAccountException;
import com.final_project.daily_operations.exception.ToMuchBalanceAccountException;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BalanceServiceValidation {


    public static final String ACCOUNT_WITH_THIS_CURRENCY_ALREADY_EXISTS = "Account with this currency already exists";
    public static final String USER_REACHED_MAXIMUM_ALLOWED_ACCOUNTS_COUNT = "User reached maximum allowed accounts count";

    public void isValidAddBalanceRequest(Currency currency, Customer customer) throws DuplicateCurrencyAccountException, ToMuchBalanceAccountException {
        boolean empty = customer.getBalances().stream()
                .filter(balance -> balance.getCurrency().getCode().equals(currency.getCode()))
                .findAny().isEmpty();

        if (!empty) throw new DuplicateCurrencyAccountException(ACCOUNT_WITH_THIS_CURRENCY_ALREADY_EXISTS);

        if (customer.getBalances().size() > 5)
            throw new ToMuchBalanceAccountException(USER_REACHED_MAXIMUM_ALLOWED_ACCOUNTS_COUNT);
    }
}
