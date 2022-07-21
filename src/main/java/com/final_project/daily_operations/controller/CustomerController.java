package com.final_project.daily_operations.controller;

import com.auth0.jwt.JWT;
import com.final_project.daily_operations.dto.CustomerDto;
import com.final_project.daily_operations.exception.EmailDoesNotExistException;
import com.final_project.daily_operations.exception.EmptyFieldsException;
import com.final_project.daily_operations.exception.TakenUsernameException;
import com.final_project.daily_operations.exception.UUIDExpiredOrDoesNotExistException;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.service.for_controller.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class CustomerController {
    private CustomerService customerService;
    private CustomerDto customerDto;

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> saveNewCustomer(@RequestBody Customer customer)
            throws TakenUsernameException, EmptyFieldsException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerDto.getCustomerDto(customerService.saveNewCustomer(customer)));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity
                .ok()
                .body(customerDto.getListOfCustomersDto(customerService.getAllCustomers()));
    }

    @GetMapping("/userWithToken")
    public ResponseEntity<CustomerDto> getCustomerByUsername(@RequestHeader("access_token") String token) {
        String username = JWT.decode(token).getClaim("sub").asString(); //TODO atskira klase
        System.out.println("***** " + username);
        return ResponseEntity
                .ok()
                .body(customerDto.getCustomerDto(customerService.getCustomerByUsername(username)));
    }

    @GetMapping("/forgot/email={email}") //TODO apsauga nuo spamo gali kiti scopai
    public ResponseEntity sendPasswordRecoverLink(@PathVariable(name = "email") String email) throws EmailDoesNotExistException {
        customerService.sendPasswordRecoveryLink(email);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @GetMapping("/recovery/{uuid}")
    public ResponseEntity sendNewPassword(@PathVariable(name = "uuid") String uuid) throws UUIDExpiredOrDoesNotExistException {
        customerService.sendNewPassword(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<CustomerDto> login(@RequestBody Customer customer){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerDto.getCustomerDto(customerService.getCustomerByUsername(customer.getUsername())));
    }
}
