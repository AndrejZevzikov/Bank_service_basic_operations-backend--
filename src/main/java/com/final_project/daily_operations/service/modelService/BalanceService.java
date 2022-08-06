package com.final_project.daily_operations.service.modelService;

import com.final_project.daily_operations.dto.TransactionDto;
import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.helper.AccountNumberGenerator;
import com.final_project.daily_operations.helper.JwtDecoder;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.model.Transaction;
import com.final_project.daily_operations.repostory.BalanceRepository;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.validation.BalanceServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class BalanceService {

    public static final String ACCOUNT_NUMBER_IN_DATABASE_DOES_NOT_EXIST = "Account number %s in Database does not exist";
    public static final String CURRENCY_DO_NOT_EXIST = "Currency with id %d doesn't exist";
    public static final String ADMIN = "ADMIN";
    private final BalanceRepository balanceRepository;
    private final CustomerService customerService;
    private final CurrencyRepository currencyRepository;
    private final BalanceServiceValidation balanceServiceValidation;
    private final AccountNumberGenerator accountNumberGenerator;
    private final JwtDecoder jwtDecoder;

    public List<Balance> getBalances(final String token) throws NoSuchObjectInDatabaseException {
        String username = jwtDecoder.getUsername(token);
        String authority = customerService.getCustomerByUsername(username).getAuthority().getAuthority();
        if (authority.equals(ADMIN)) {
            return balanceRepository.findAll();
        }
        return customerService.getCustomerByUsername(username).getBalances();
    }

    public List<Balance> addNewBalance(final String token, final Long currencyId) throws InvalidBalanceException, NoSuchObjectInDatabaseException {

        final Customer customer = customerService.getCustomerByUsername(jwtDecoder.getUsername(token));
        final Currency currency = currencyRepository.findById(currencyId).orElseThrow(
                () -> new NoSuchElementException(String.format(CURRENCY_DO_NOT_EXIST, currencyId)));
        log.info("Creating new account for user {} with currency id {}", customer.getUsername(), currencyId);
        balanceServiceValidation.isValidAddBalanceRequest(currency, customer);
        final String accountNumber = accountNumberGenerator.generate();
        balanceRepository.save(Balance.builder()
                .customer(customer)
                .accountNumber(accountNumber)
                .currency(currency)
                .amount(1.0)
                .build());
        return getBalances(token);
    }

    public Balance updatePayerBalance(final Transaction transaction) throws NoSuchObjectInDatabaseException {
        Balance balance = balanceRepository.findByAccountNumber(transaction.getPayerAccountNumber()).orElseThrow(
                () -> new NoSuchObjectInDatabaseException(String.format(
                        ACCOUNT_NUMBER_IN_DATABASE_DOES_NOT_EXIST, transaction.getPayerAccountNumber())));
        balance.setAmount(balance.getAmount() - transaction.getAmount());
        return balanceRepository.save(balance);
    }

    public Balance updateReceiverBalance(final Transaction transaction) throws NoSuchObjectInDatabaseException {
        Balance balance = balanceRepository.findByAccountNumber(transaction.getReceiverAccountNumber()).orElseThrow(
                () -> new NoSuchObjectInDatabaseException(String.format(
                        ACCOUNT_NUMBER_IN_DATABASE_DOES_NOT_EXIST, transaction.getReceiverAccountNumber())));
        balance.setAmount(balance.getAmount() + transaction.getAmount());
        return balanceRepository.save(balance);
    }

    public boolean isInnerPayment(Transaction transaction){
        if (balanceRepository.findByAccountNumber(transaction.getReceiverAccountNumber()).isPresent()){
            return true;
        }
        return false;
    }
}