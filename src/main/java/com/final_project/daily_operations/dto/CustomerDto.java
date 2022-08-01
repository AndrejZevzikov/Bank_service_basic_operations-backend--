package com.final_project.daily_operations.dto;

import com.final_project.daily_operations.model.Customer;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private String authority;
}
