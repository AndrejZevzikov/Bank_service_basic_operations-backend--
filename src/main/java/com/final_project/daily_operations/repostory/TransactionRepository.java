package com.final_project.daily_operations.repostory;

import com.final_project.daily_operations.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transaction n ORDER BY local_date DESC LIMIT ?", nativeQuery = true)
    List<Transaction> findLastTransactionsByGivenCount(Integer count);

    @Query(value = "SELECT * FROM transaction t WHERE (t.payer_account_number = :number OR t.receiver_account_number = :number)" +
            " ORDER BY local_date DESC LIMIT :count", nativeQuery = true)
    List<Transaction> findLastTransactionsByGivenCountAndAccountNumber(
            @Param("number") String accountNumber, @Param("count") Integer count);
}