package com.final_project.daily_operations.controller;

import com.auth0.jwt.JWT;
import com.final_project.daily_operations.dto.BalanceDto;
import com.final_project.daily_operations.exception.DuplicateCurrencyAccountException;
import com.final_project.daily_operations.exception.NoSuchObjectInDatabaseException;
import com.final_project.daily_operations.exception.ToMuchBalanceAccountException;
import com.final_project.daily_operations.exception.ModelDoesNotExistException;
import com.final_project.daily_operations.mapper.mapperDto.MapperDto;
import com.final_project.daily_operations.service.for_controller.BalanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/balance")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class BalanceController {

    private final BalanceService balanceService;
    private final MapperDto mapperDto;

    @GetMapping
    public ResponseEntity<List<BalanceDto>> getMyBalance(@RequestHeader("Authorization") String token) throws NoSuchObjectInDatabaseException {
        String username = JWT.decode(token.substring("Bearer ".length())).getClaim("sub").asString();
        log.info("searching user with username: {}", username);
        return ResponseEntity
                .ok()
                .body(mapperDto.toBalanceDtoList(balanceService.getBalances(username)));
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<List<BalanceDto>> addNewBalance(
            @RequestHeader("Authorization") String token,
            @PathVariable(name = "id") Long id) throws DuplicateCurrencyAccountException, ModelDoesNotExistException, ToMuchBalanceAccountException, NoSuchObjectInDatabaseException {
        String username = JWT.decode(token.substring("Bearer ".length())).getClaim("sub").asString();
        return ResponseEntity
                .ok()
                .body(mapperDto.toBalanceDtoList(balanceService.addNewBalance(username,id)));
    }

    @GetMapping("total/user/{id}")
    public ResponseEntity<Double> getTotalAmount(@PathVariable(name = "id") Long id) {
        return null;
    }

}
