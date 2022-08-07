package com.final_project.daily_operations.repostory;

import com.final_project.daily_operations.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {

    Optional<Balance> findByAccountNumber(String accountNumber);
}