package com.final_project.daily_operations.validation;

import com.final_project.daily_operations.exception.InvalidBalanceException;
import com.final_project.daily_operations.exception.NoAccessException;
import com.final_project.daily_operations.exception.NoEnoughMoneyException;
import com.final_project.daily_operations.exception.WrongCurrencyException;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionServiceValidation {

    public static final String USER_DO_NOT_HAVE_ACCESS_MAKE_PAYMENT = "User with id %s do not have access make" +
            " payment from account number: %s";
    public static final String WRONG_CURRENCY = "Account currency and transaction currency do not match";
    public static final String NO_ENOUGH_MONEY = "No enough money in account %s. Actual amount %f in balance left: %f";

    public void isPaymentValid(final Transaction transaction, final Customer customer) throws InvalidBalanceException {
        Balance validBalance = customer.getBalances().stream()
                .filter(balance -> balance.getAccountNumber().equals(transaction.getPayerAccountNumber()))
                .findFirst().orElseThrow(() -> new NoAccessException(String.format(USER_DO_NOT_HAVE_ACCESS_MAKE_PAYMENT,
                        customer.getId(), transaction.getPayerAccountNumber())));
        if (!validBalance.getCurrency().equals(transaction.getCurrency())) {
            throw new WrongCurrencyException(WRONG_CURRENCY);
        }
        if (transaction.getAmount() > validBalance.getAmount()) {
            throw new NoEnoughMoneyException(String.format(
                    NO_ENOUGH_MONEY, validBalance.getAccountNumber(), validBalance.getAmount(), transaction.getAmount()));
        }
    }
}