package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.CustomerDto;
import com.final_project.daily_operations.exception.EmptyFieldsException;
import com.final_project.daily_operations.exception.TakenUsernameException;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class CustomerController {
    private CustomerService customerService;
    private CustomerDto customerDto;

    @PostMapping
    public ResponseEntity<Customer> saveNewCustomer(@RequestBody Customer customer)
            throws TakenUsernameException, EmptyFieldsException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.saveNewCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        return ResponseEntity
                .ok()
                .body(customerDto.getListOfCustomersDto(customerService.getAllCustomers()));
    }

    @GetMapping("/username={username}")
    public ResponseEntity<CustomerDto> getCustomerByUsername(@PathVariable(name = "username")String username){
        return ResponseEntity.ok().body(customerDto.getCustomerDto(customerService.getCustomerByUsername(username)));
    }

//    @GetMapping("/login")
//    public ResponseEntity<CustomerDto> login(@RequestBody Customer customer){
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(customerDto.getCustomerDto(customerService.getCustomerByUsername(customer.getUsername())));
//    }
}
