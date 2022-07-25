package com.final_project.daily_operations.service.for_controller;

import com.final_project.daily_operations.exception.DuplicateCurrencyAccountException;
import com.final_project.daily_operations.exception.ToMuchBalanceAccountException;
import com.final_project.daily_operations.exception.ModelDoesNotExistException;
import com.final_project.daily_operations.helper.AccountNumberGenerator;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.repostory.BalanceRepository;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.repostory.CustomerRepository;
import com.final_project.daily_operations.validation.BalanceServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BalanceService {

    private BalanceRepository balanceRepository;
    private CustomerService customerService;
    private CurrencyRepository currencyRepository;
    private CustomerRepository customerRepository;
    private BalanceServiceValidation balanceServiceValidation;
    private AccountNumberGenerator accountNumberGenerator;


    public List<Balance> getBalances(String username) throws ModelDoesNotExistException {
        String authority = customerService.getCustomerByUsername(username).getAuthority().getAuthority();
        if (authority.equals("ADMIN")) { //TODO Authority enumas
            return balanceRepository.findAll();
        }
        return customerService.getCustomerByUsername(username).getBalances();//TODO Efektyvumui gal custom query
    }

    public List<Balance> addNewBalance(String username, Long currencyId) throws DuplicateCurrencyAccountException, ToMuchBalanceAccountException, ModelDoesNotExistException {
        log.info("Creating new account for user {} with currency id {}", username, currencyId);
        Customer customer = customerService.getCustomerByUsername(username);
        Currency currency = currencyRepository.findById(currencyId).get();
        balanceServiceValidation.isValidAddBalanceRequest(currency, customer);
        String accountNumber = accountNumberGenerator.generate();
        balanceRepository.save(Balance.builder()
                .customer(customer)
                .accountNumber(accountNumber)
                .currency(currency)
                .amount(1.0)
                .build());
        return getBalances(customer.getUsername());
    }
}
