package com.final_project.daily_operations.service;

import com.final_project.daily_operations.exception.EmptyFieldsException;
import com.final_project.daily_operations.exception.TakenUsernameException;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.repostory.CustomerRepository;
import com.final_project.daily_operations.validation.CustomerServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerServiceValidation customerServiceValidation;

    public Customer saveNewCustomer(Customer customer) throws TakenUsernameException, EmptyFieldsException {
        customerServiceValidation.IsValidRegistrationInformation(customer);
        log.info("Saving user by username: {} and email {}", customer.getUsername(), customer.getEmail());
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
}
