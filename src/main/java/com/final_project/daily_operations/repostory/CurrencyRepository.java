package com.final_project.daily_operations.repostory;

import com.final_project.daily_operations.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {

}
