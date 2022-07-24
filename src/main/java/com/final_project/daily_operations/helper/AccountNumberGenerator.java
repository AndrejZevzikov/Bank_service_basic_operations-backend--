package com.final_project.daily_operations.helper;

import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.repostory.BalanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AccountNumberGenerator {

    private BalanceRepository balanceRepository;

    public String generate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String year = String.valueOf(localDateTime.getYear());
        String month = oneDigitConverterToTwoDigits(String.valueOf(localDateTime.getMonthValue()));
        String day = oneDigitConverterToTwoDigits(String.valueOf(localDateTime.getDayOfMonth()));
        String prefix = "INV";
        String preNumber = prefix + year + month + day;
        List<String> allAccountsInThisDay = balanceRepository.findAll()
                .stream()
                .map(Balance::getAccountNumber)
                .filter(s -> s.contains(prefix))
                .collect(Collectors.toList());
        if (allAccountsInThisDay.size() != 0) {
            Integer maxNumber = allAccountsInThisDay.stream()
                    .map(s -> s.substring(11))
                    .map(Integer::valueOf)
                    .max(Integer::compareTo).get();
            return preNumber + (maxNumber + 1);
        }
        return preNumber + "1";
    }

    private String oneDigitConverterToTwoDigits(String digit) {
        return digit.length() == 1 ? ("0" + digit) : digit;
    }
}