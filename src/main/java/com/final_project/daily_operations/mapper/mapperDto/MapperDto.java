package com.final_project.daily_operations.mapper.mapperDto;

import com.final_project.daily_operations.dto.BalanceDto;
import com.final_project.daily_operations.dto.CurrencyRateDto;
import com.final_project.daily_operations.dto.CustomerDto;
import com.final_project.daily_operations.dto.NewsDto;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.CurrencyRate;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.model.News;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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
}
