package com.final_project.daily_operations.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(mappedBy = "currency")
    private Balance balance;
    @OneToMany(mappedBy = "currency")
    private List<CurrencyRate> currencyRates;
    @OneToMany(mappedBy = "currency")
    private List<Transaction> transactions;
}
