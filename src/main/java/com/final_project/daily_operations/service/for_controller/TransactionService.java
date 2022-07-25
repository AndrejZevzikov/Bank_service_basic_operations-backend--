package com.final_project.daily_operations.service.for_controller;

import com.final_project.daily_operations.exception.ModelDoesNotExistException;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Transaction;
import com.final_project.daily_operations.repostory.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {

    private final CustomerService customerService;
    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactions(String username) throws ModelDoesNotExistException {

        if (customerService.getCustomerByUsername(username).getAuthority().getAuthority().equals("ADMIN")){
            return transactionRepository.findLastTransactionsByGivenCount(5);
        }
        List<String> accountNumbers = customerService.getCustomerByUsername(username).getBalances().stream()
                .map(Balance::getAccountNumber)
                .collect(Collectors.toList());

        List<Transaction> transactions = new ArrayList<>();
        accountNumbers.forEach(s -> {
            transactions.addAll(transactionRepository.findLastTransactionsByGivenCountAndCustomer(s,5));
        });


        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getLocalDate))
                .collect(Collectors.toList());
    }
}
