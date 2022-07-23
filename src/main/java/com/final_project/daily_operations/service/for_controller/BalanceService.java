package com.final_project.daily_operations.service.for_controller;

import com.final_project.daily_operations.exception.UsernameDoesNotExistException;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.repostory.BalanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BalanceService {

    private BalanceRepository balanceRepository;
    private CustomerService customerService;

    public List<Balance> getAllBalances(){
        //TODO validation?
        return balanceRepository.findAll();
    }

    public List<Balance> getMyBalance(String username) throws UsernameDoesNotExistException {
        return customerService.getCustomerByUsername(username).getBalances();//TODO Efektyvumui gal custom query
    }
}
