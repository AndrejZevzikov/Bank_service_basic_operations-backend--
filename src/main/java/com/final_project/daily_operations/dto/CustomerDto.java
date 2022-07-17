package com.final_project.daily_operations.dto;

import com.final_project.daily_operations.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class CustomerDto {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private String authority;

    public CustomerDto getCustomerDto(Customer customer) {
        return new CustomerDto(customer.getUsername(), customer.getPassword(), customer.getEmail()
                , customer.getFirstName() ,customer.getLastName(), customer.getIdentificationNumber()
                ,customer.getAuthority().getAuthority());
    }

    public List<CustomerDto> getListOfCustomersDto(List<Customer> customers){
        return customers.stream()
                .map(this::getCustomerDto)
                .collect(Collectors.toList());
    }
}
