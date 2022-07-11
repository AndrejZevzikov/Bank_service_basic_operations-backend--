package com.final_project.daily_operations.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Customer payer;
    @OneToOne
    private Customer receiver;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    private LocalDate localDate;
}
