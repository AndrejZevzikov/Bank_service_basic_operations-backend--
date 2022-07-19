package com.final_project.daily_operations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    @OneToMany(mappedBy = "currency")
    private List<Balance> balances;
    @OneToMany(mappedBy = "currency")
    private List<CurrencyRate> currencyRates;
    @OneToMany(mappedBy = "currency")
    private List<Transaction> transactions;
}
