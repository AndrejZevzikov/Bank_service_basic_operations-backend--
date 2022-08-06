package com.final_project.daily_operations.controller;

import com.final_project.daily_operations.dto.BalanceDto;
import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.service.modelService.BalanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/balance")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class BalanceController {

    private final BalanceService balanceService;
    private final MapperDto mapperDto;

    @GetMapping
    public ResponseEntity<List<BalanceDto>> getMyBalance(@RequestHeader(AUTHORIZATION) String token)
            throws NoSuchObjectInDatabaseException {
        return ResponseEntity
                .ok()
                .body(mapperDto.toBalanceDtoList(balanceService.getBalances(token)));
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<List<BalanceDto>> addNewBalance(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable(name = "id") Long id) throws InvalidBalanceException, NoSuchObjectInDatabaseException {
        return ResponseEntity
                .ok()
                .body(mapperDto.toBalanceDtoList(balanceService.addNewBalance(token,id)));
    }

    @GetMapping("/total/user/{id}")
    public ResponseEntity<Double> getTotalAmount(@PathVariable(name = "id") Long id) {
        return null;
    }

}
