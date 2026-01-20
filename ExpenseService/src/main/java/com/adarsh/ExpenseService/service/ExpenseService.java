package com.adarsh.ExpenseService.service;

import com.adarsh.ExpenseService.dto.ExpenseDto;
import com.adarsh.ExpenseService.entities.Expense;
import com.adarsh.ExpenseService.repository.ExpenseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    public boolean createCurrency(ExpenseDto expenseDto){
        setCurrency(expenseDto);
        try{
            Expense expense = Expense.builder()
                    .externalId(expenseDto.getExternalId())
                    .userId(expenseDto.getUserId())
                    .amount(expenseDto.getAmount())
                    .merchant(expenseDto.getMerchant())
                    .currency(expenseDto.getCurrency())
                    .createdAt(expenseDto.getCreatedAt())
                    .build();
             expenseRepository.save(expense);
             return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateExpense( ExpenseDto expenseDto ){
        Optional<Expense> expenseFoundOpt = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(),expenseDto.getExternalId());
        if(expenseFoundOpt.isEmpty()){
            return false;
        }
        Expense expense = expenseFoundOpt.get();
        expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency()) ? expenseDto.getCurrency() : expense.getCurrency() );
        expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant()) ? expenseDto.getMerchant() : expense.getMerchant() );
        expense.setAmount( expenseDto.getAmount() );
        expenseRepository.save(expense);
        return true;
    }

    public List<ExpenseDto> getExpenses(String userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<ExpenseDto> getExpensesByUserIdAndTimeRange(String userId, Timestamp startTime, Timestamp endTime) {
        List<Expense> expenseList = expenseRepository.findByUserIdAndCreatedAtBetween(userId, startTime, endTime);
        return objectMapper.convertValue(expenseList, new TypeReference<List<ExpenseDto>>() { } );
    }

    private void setCurrency(ExpenseDto expenseDto) {
        if(Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("inr");
        }
    }

    private ExpenseDto toDto(Expense expense) {
        return ExpenseDto.builder()
                .externalId(expense.getExternalId())
                .amount(expense.getAmount())
                .userId(expense.getUserId())
                .merchant(expense.getMerchant())
                .currency(expense.getCurrency())
                .createdAt(expense.getCreatedAt())
                .build();
    }
}
