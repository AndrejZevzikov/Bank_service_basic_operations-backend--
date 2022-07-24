package com.final_project.daily_operations.dto;

import com.final_project.daily_operations.model.Currency;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CurrencyDto {

    private Long id;
    private String code;
}
