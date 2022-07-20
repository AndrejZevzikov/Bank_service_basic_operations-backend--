package com.final_project.daily_operations.helper;

import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.repostory.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountNumberGeneratorTest {

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private AccountNumberGenerator accountNumberGenerator;

    @Test
    void TestGenerateFirstAccountInDay() {
        when(balanceRepository.findAll()).thenReturn(new ArrayList<>());
        assertThat(accountNumberGenerator.generate()).isEqualTo("INV202207191");
    }

    @Test
    void TestGenerateNotFirstAccountInDay() { //TODO sugalvot kaip data neharkodint
        List<Balance> allBalances = List.of(
                new Balance(null,"INV202207192",null,null,null),
                new Balance(null,"INV202207191",null,null,null));
        when(balanceRepository.findAll()).thenReturn(allBalances);
        assertThat(accountNumberGenerator.generate()).isEqualTo("INV202207193");
    }
}