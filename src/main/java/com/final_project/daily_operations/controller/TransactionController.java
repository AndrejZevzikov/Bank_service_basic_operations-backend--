package com.final_project.daily_operations.controller;

import com.auth0.jwt.JWT;
import com.final_project.daily_operations.dto.TransactionDto;
import com.final_project.daily_operations.exception.ModelDoesNotExistException;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.service.for_controller.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final MapperDto mapperDto;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions(@RequestHeader("Authorization") String token) throws ModelDoesNotExistException {
        String username = JWT.decode(token.substring("Bearer ".length())).getClaim("sub").asString();
        return ResponseEntity.ok().body(mapperDto.toTransactionDtoList(transactionService.getTransactions(username)));
    }
}
