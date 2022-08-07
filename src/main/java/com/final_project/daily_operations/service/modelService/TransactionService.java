package com.final_project.daily_operations.service.modelService;

import com.final_project.daily_operations.dto.TransactionDto;
import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.helper.JwtDecoder;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.model.Transaction;
import com.final_project.daily_operations.repostory.TransactionRepository;
import com.final_project.daily_operations.validation.BalanceServiceValidation;
import com.final_project.daily_operations.validation.TransactionServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    public static final String ADMIN = "ADMIN";
    private final CustomerService customerService;
    private final TransactionRepository transactionRepository;
    private final JwtDecoder jwtDecoder;
    private final TransactionServiceValidation transactionServiceValidation;
    private final BalanceService balanceService;
    private final BalanceServiceValidation balanceServiceValidation;

    public List<Transaction> getTransactionsLimitedByGivenNumber(final String token, int limitCount) throws NoSuchObjectInDatabaseException {
        Customer customer = customerService.getCustomerByUsername(jwtDecoder.getUsername(token));
        List<Transaction> transactions = new ArrayList<>();

        if (customer.getAuthority().getAuthority().equals(ADMIN)) {
            return transactionRepository.findLastTransactionsByGivenCount(limitCount).stream()
                    .sorted(Comparator.comparing(Transaction::getLocalDate).reversed())
                    .collect(Collectors.toList());
        }
        customer.getBalances().stream()// TODO SQL IN panaudot
                .map(Balance::getAccountNumber)
                .forEach(s -> transactions.addAll(
                        transactionRepository
                                .findLastTransactionsByGivenCountAndAccountNumber(s, limitCount)));

        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getLocalDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Transaction> getAllTransactions(String token) throws NoSuchObjectInDatabaseException {
        return getTransactionsLimitedByGivenNumber(token,100);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Transaction makePayment(final Transaction transaction, final String token)
            throws NoSuchObjectInDatabaseException, InvalidBalanceException, InvalidCurrencyException {
        Customer customer = customerService.getCustomerByUsername(jwtDecoder.getUsername(token));
        transactionServiceValidation.isPaymentValid(transaction, customer);
        transaction.setIsInnerTransaction(balanceService.isInnerPayment(transaction));
        transaction.setLocalDate(LocalDate.now());
        log.info("updating payer balance");
        balanceService.updatePayerBalance(transaction);
        if (transaction.getIsInnerTransaction()) {
            balanceServiceValidation.isCurrencyValid(transaction.getCurrency(),transaction.getReceiverAccountNumber());
            log.info("updating receiver balance");
            balanceService.updateReceiverBalance(transaction);
        }
        log.info("saving transaction");
        return transactionRepository.save(transaction);
    }

}