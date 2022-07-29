package com.final_project.daily_operations.service.permanent;

import com.final_project.daily_operations.service.runtime.CurrencyRuntimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.TimerTask;

@Configuration
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class CurrencyRatesPeriodicalUpdater extends TimerTask  {

    private CurrencyRuntimeService currencyRuntimeService;

    @Override
    @Scheduled(cron = "0 0 8 * * *")
    public void run() {
        try {
            currencyRuntimeService.updateCurrentCurrencyRates();
        } catch (IOException e) {
            log.error("Can't update currency rates");
        }
    }

}
