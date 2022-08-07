package com.final_project.daily_operations.preparedData;

import com.final_project.daily_operations.model.Authority;
import com.final_project.daily_operations.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomerPreparedData {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public List<Customer> setUpCustomers() {
        List<Customer> customers = new ArrayList<>();

        Customer customer1 = Customer.builder()
                .firstName("John")
                .lastName("Daw")
                .email("andrejzevz13@gmail.com")
                .identificationNumber("89546621")
                .isEnabled(Boolean.TRUE)
                .username("Johnny123")
                .password(passwordEncoder.encode("test"))
                .authority(new Authority(null, "ADMIN", new ArrayList<>()))
                .news(new ArrayList<>())
                .build();
        customer1.getAuthority().getCustomers().add(customer1);

        Customer customer2 = Customer.builder()
                .firstName("Tom")
                .lastName("Gates")
                .email("tom@tom.lt")
                .identificationNumber("54643131")
                .isEnabled(Boolean.TRUE)
                .username("TomasG")
                .password(passwordEncoder.encode("test1"))
                .authority(new Authority(null, "CLIENT", new ArrayList<>()))
                .news(new ArrayList<>())
                .build();

        customer2.getAuthority().getCustomers().add(customer2);
        customers.add(customer1);
        customers.add(customer2);
        return customers;
    }
}
