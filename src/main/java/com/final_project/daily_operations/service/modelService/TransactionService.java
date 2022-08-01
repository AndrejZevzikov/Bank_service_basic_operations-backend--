package com.final_project.daily_operations.service.modelService;

import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.helper.JwtDecoder;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.model.Transaction;
import com.final_project.daily_operations.repostory.TransactionRepository;
import com.final_project.daily_operations.validation.TransactionServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    public static final String ADMIN = "ADMIN";
    public static final int LAST_FIVE_TRANSACTION = 5;
    private final CustomerService customerService;
    private final TransactionRepository transactionRepository;
    private final JwtDecoder jwtDecoder;
    private final TransactionServiceValidation transactionServiceValidation;
    private final BalanceService balanceService;

    public List<Transaction> getTransactions(final String token) throws NoSuchObjectInDatabaseException {
        Customer customer = customerService.getCustomerByUsername(jwtDecoder.getUsername(token));
        List<Transaction> transactions = new ArrayList<>();

        if (customer.getAuthority().getAuthority().equals(ADMIN)) {
            return transactionRepository.findLastTransactionsByGivenCount(LAST_FIVE_TRANSACTION);
        }
        customer.getBalances().stream()// TODO SQL IN panaudot
                .map(Balance::getAccountNumber)
                .forEach(s -> transactions.addAll(
                        transactionRepository
                                .findLastTransactionsByGivenCountAndAccountNumber(s, LAST_FIVE_TRANSACTION)));

        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getLocalDate))
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Transaction makePayment(final Transaction transaction, final String token)
            throws NoSuchObjectInDatabaseException, InvalidBalanceException {
        Customer customer = customerService.getCustomerByUsername(jwtDecoder.getUsername(token));
        transactionServiceValidation.isPaymentValid(transaction, customer);
        log.info("updating payer balance");
        balanceService.updatePayerBalance(transaction);
        if (transaction.getIsInnerTransaction()) {
            log.info("updating receiver balance");
            balanceService.updateReceiverBalance(transaction);
        }
        log.info("saving transaction");
        return transactionRepository.save(transaction);
    }
}