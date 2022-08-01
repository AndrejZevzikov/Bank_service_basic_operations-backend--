package com.final_project.daily_operations.service.for_controller;

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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final CustomerService customerService;
    private final TransactionRepository transactionRepository;
    private final JwtDecoder jwtDecoder;
    private final TransactionServiceValidation transactionServiceValidation;
    private final BalanceService balanceService;

    public List<Transaction> getTransactions(String username) throws NoSuchObjectInDatabaseException {

        if (customerService.getCustomerByUsername(username).getAuthority().getAuthority().equals("ADMIN")) {
            return transactionRepository.findLastTransactionsByGivenCount(5);
        }
        List<String> accountNumbers = customerService.getCustomerByUsername(username).getBalances().stream()
                .map(Balance::getAccountNumber)
                .collect(Collectors.toList());

        List<Transaction> transactions = new ArrayList<>();
        accountNumbers.forEach(s ->
                transactions.addAll(transactionRepository.findLastTransactionsByGivenCountAndCustomer(s, 5)));


        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getLocalDate))
                .collect(Collectors.toList());
    }

    public Transaction makePayment(Transaction transaction, String token) throws NoSuchObjectInDatabaseException, NoAccessException, WrongCurrencyException, NoEnoughMoneyException {
        Customer customer = customerService.getCustomerByUsername(jwtDecoder.getUsername(token)); //TODO gal bendra interfaca visiem exceptionams
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
