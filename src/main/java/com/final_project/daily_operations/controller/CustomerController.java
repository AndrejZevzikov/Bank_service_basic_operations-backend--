package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.CustomerDto;
import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.service.modelService.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8081"})
@AllArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;
    private final MapperDto mapperDto;

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> saveNewCustomer(@RequestBody Customer customer)
            throws TakenUsernameException, EmptyFieldsException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapperDto.toCustomerDto(customerService.saveNewCustomer(customer)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity
                .ok()
                .body(mapperDto.toCustomerDtoList(customerService.getAllCustomers()));
    }

    @GetMapping("/get")
    public ResponseEntity<CustomerDto> getCustomerWithToken(@RequestHeader(AUTHORIZATION) String token)
            throws NoSuchObjectInDatabaseException {
        return ResponseEntity.ok().body(mapperDto.toCustomerDto(customerService.getCustomerByToken(token)));
    }

    @GetMapping("/valid")
    public ResponseEntity<CustomerDto> isTokenValid(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userWithToken")
    public ResponseEntity<CustomerDto> getCustomerByToken(@RequestHeader("access_token") String token)
            throws NoSuchObjectInDatabaseException {
        return ResponseEntity
                .ok()
                .body(mapperDto.toCustomerDto(customerService.getCustomerByToken(token)));
    }

    @GetMapping("/forgot/email={email}") //TODO apsauga nuo spamo gali kiti scopai
    public ResponseEntity sendPasswordRecoverLink(@PathVariable(name = "email") String email) throws EmailDoesNotExistException {
        customerService.sendPasswordRecoveryLink(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/recovery/{uuid}")
    public ResponseEntity sendNewPassword(@PathVariable(name = "uuid") String uuid)
            throws UUIDExpiredOrDoesNotExistException, NoSuchObjectInDatabaseException {
        customerService.sendNewPassword(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<CustomerDto> login(@RequestBody Customer customer) throws NoSuchObjectInDatabaseException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapperDto.toCustomerDto(customerService.getCustomerByUsername(customer.getUsername())));
    }

    @GetMapping("/total_balance")
    public ResponseEntity<Double> getTotalBalanceAmountInEuro(@RequestHeader(AUTHORIZATION) String token)
            throws NoSuchObjectInDatabaseException {
        return ResponseEntity.ok().body(customerService.getCustomerTotalAmountInEurFromToken(token));
    }

    @GetMapping("search/{phrase}")
    public ResponseEntity<List<CustomerDto>> getFilteredCustomerListByGivenPhrase(@PathVariable(name = "phrase") String phrase){
        return ResponseEntity
                .ok()
                .body(mapperDto.toCustomerDtoList(customerService.getFilteredListOfCustomers(phrase)));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<CustomerDto>> deleteCustomerById(@PathVariable(name = "id")Long id) throws NoSuchObjectInDatabaseException {
        return ResponseEntity
                .ok()
                .body(mapperDto.toCustomerDtoList(customerService.deleteCustomerById(id)));
    }
}
