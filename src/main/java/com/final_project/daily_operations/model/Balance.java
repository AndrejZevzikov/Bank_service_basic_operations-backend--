package com.final_project.daily_operations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}