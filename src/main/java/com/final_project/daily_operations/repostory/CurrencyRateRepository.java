package com.final_project.daily_operations.repostory;

import com.final_project.daily_operations.model.Currency;
import com.final_project.daily_operations.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    @Query("SELECT c FROM CurrencyRate c WHERE c.date = :date and c.currency = :currency")
    Optional<CurrencyRate> getCurrencyRateByDateAndCurrencyId(
            @Param("date") LocalDate date, @Param("currency") Currency currency);

    @Query(value = "SELECT c.date FROM currency_rate c ORDER BY c.date DESC LIMIT 1", nativeQuery = true)
    Optional<LocalDate> getLastUpdateDate();

    @Query("SELECT c FROM CurrencyRate c WHERE c.date = :date")
    Optional<List<CurrencyRate>> getCurrencyRatesByGiveDate(@Param("date") LocalDate date);
}