package com.final_project.daily_operations.dto;

import lombok.*;

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