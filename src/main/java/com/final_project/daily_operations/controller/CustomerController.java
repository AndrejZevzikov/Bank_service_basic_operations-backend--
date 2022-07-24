package com.final_project.daily_operations.controller;

import com.auth0.jwt.JWT;
import com.final_project.daily_operations.dto.CustomerDto;
import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.service.for_controller.CustomerService;
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
    private MapperDto mapperDto;

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> saveNewCustomer(@RequestBody Customer customer)
            throws TakenUsernameException, EmptyFieldsException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapperDto.toCustomerDto(customerService.saveNewCustomer(customer)));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity
                .ok()
                .body(mapperDto.toCustomerDtoList(customerService.getAllCustomers()));
    }

    @GetMapping("/valid")
    public ResponseEntity isTokenValid(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userWithToken")
    public ResponseEntity<CustomerDto> getCustomerByUsername(@RequestHeader("access_token") String token) throws UsernameDoesNotExistException {
        String username = JWT.decode(token).getClaim("sub").asString();
        return ResponseEntity
                .ok()
                .body(mapperDto.toCustomerDto(customerService.getCustomerByUsername(username)));
    }

    @GetMapping("/forgot/email={email}") //TODO apsauga nuo spamo gali kiti scopai
    public ResponseEntity sendPasswordRecoverLink(@PathVariable(name = "email") String email) throws EmailDoesNotExistException {
        customerService.sendPasswordRecoveryLink(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/recovery/{uuid}")
    public ResponseEntity sendNewPassword(@PathVariable(name = "uuid") String uuid) throws UUIDExpiredOrDoesNotExistException {
        customerService.sendNewPassword(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<CustomerDto> login(@RequestBody Customer customer) throws UsernameDoesNotExistException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapperDto.toCustomerDto(customerService.getCustomerByUsername(customer.getUsername())));
    }

    @GetMapping("/total_balance")
    public ResponseEntity<Double> getTotalBalanceAmountInEuro(@RequestHeader("Authorization") String token) throws UsernameDoesNotExistException {
        String username = JWT.decode(token.substring("Bearer ".length())).getClaim("sub").asString();
        return ResponseEntity.ok().body(customerService.getCustomerTotalAmountInEur(username));
    }
}
