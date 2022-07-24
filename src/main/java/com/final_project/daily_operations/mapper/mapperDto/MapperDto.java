package com.final_project.daily_operations.mapper.mapperDto;

import com.final_project.daily_operations.dto.*;
import com.final_project.daily_operations.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MapperDto {


    public CurrencyRateDto toCurrencyRateDto(CurrencyRate currencyRate){
        return CurrencyRateDto.builder()
                .code(currencyRate.getCurrency().getCode())
                .date(currencyRate.getDate())
                .rate(currencyRate.getRate())
                .build();
    }

    public List<CurrencyRateDto> toCurrencyRateDtoList(List<CurrencyRate> currencyRates){
        return currencyRates.stream()
                .map(this::toCurrencyRateDto)
                .collect(Collectors.toList());
    }

    public NewsDto toNewsDto(News news){
        return NewsDto.builder()
                .content(news.getContent())
                .imageLink(news.getImageLink())
                .createDateTime(news.getCreateDateTime())
                .title(news.getTitle())
                .build();
    }

    public List<NewsDto> toNewsDtoList(List<News> newsList){
        return newsList.stream()
                .map(this::toNewsDto)
                .collect(Collectors.toList());
    }

    public CustomerDto toCustomerDto(Customer customer){
        return CustomerDto.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .authority(customer.getAuthority().getAuthority())
                .identificationNumber(customer.getIdentificationNumber())
                .build();
    }

    public List<CustomerDto> toCustomerDtoList(List<Customer> customers){
        return customers.stream()
                .map(this::toCustomerDto)
                .collect(Collectors.toList());
    }

    public BalanceDto toBalanceDto(Balance balance){
        return BalanceDto.builder()
                .accountNumber(balance.getAccountNumber())
                .amount(balance.getAmount())
                .currencyCode(balance.getCurrency().getCode())
                .build();
    }

    public List<BalanceDto> toBalanceDtoList(List<Balance> balances){
        return balances.stream()
                .map(this::toBalanceDto)
                .collect(Collectors.toList());
    }

    public CurrencyDto toCurrencyDto(Currency currency){
        return CurrencyDto.builder()
                .id(currency.getId())
                .code(currency.getCode())
                .build();
    }

    public List<CurrencyDto> toCurrencyDtoList(List<Currency> currencies){
        return currencies.stream()
                .map(this::toCurrencyDto)
                .collect(Collectors.toList());
    }

}
