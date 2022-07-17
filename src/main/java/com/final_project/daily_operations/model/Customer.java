package com.final_project.daily_operations.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "authority_id")
    private Authority authority;
    @Column(name = "enabled")
    private Boolean isEnabled;
    @OneToMany(mappedBy = "customer")
    private List<Balance> balances;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<News> news;
}
