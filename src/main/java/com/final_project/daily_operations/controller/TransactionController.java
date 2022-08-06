package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.TransactionDto;
import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.mapper.mapperDto.MapperFromDto;
import com.final_project.daily_operations.service.modelService.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(path = "/transactions")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final MapperDto mapperDto;
    private final MapperFromDto mapperFromDto;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions(@RequestHeader(AUTHORIZATION) String token)
            throws NoSuchObjectInDatabaseException {
        return ResponseEntity
                .ok()
                .body(mapperDto.toTransactionDtoList(transactionService.getTransactionsLimitedByGivenNumber(token,5)));
    }

    @GetMapping("/all")
    ResponseEntity<List<TransactionDto>> getAllTransactions(@RequestHeader(AUTHORIZATION) String token)
            throws NoSuchObjectInDatabaseException {
        return ResponseEntity.ok().body(mapperDto.toTransactionDtoList(transactionService.getAllTransactions(token)));
    }

    @PostMapping
    public ResponseEntity<TransactionDto> makePayment(@RequestHeader(AUTHORIZATION) String token,
                                                      @RequestBody TransactionDto transactionDto)
            throws InvalidBalanceException, NoSuchObjectInDatabaseException, InvalidCurrencyException {
        return ResponseEntity
                .ok()
                .body(mapperDto.toTransactionDto(transactionService.makePayment(
                        mapperFromDto.fromTransactionDto(transactionDto), token)));
    }
}
