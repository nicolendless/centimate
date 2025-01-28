package com.example.expense_tracker.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.expense_tracker.dtos.ExpenseDto;
import com.example.expense_tracker.entities.Category;
import com.example.expense_tracker.entities.Expense;
import com.example.expense_tracker.exceptions.ExpenseNotFoundException;
import com.example.expense_tracker.mappers.ExpenseMapper;
import com.example.expense_tracker.repositories.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;    

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;        
    }

    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        Expense expense = ExpenseMapper.toEntity(expenseDto);    
        return ExpenseMapper.toDto(expenseRepository.save(expense));
    }

    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto) {
        Expense expense = this.expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseNotFoundException(id));        
        
        expense.setTitle(expenseDto.getTitle());
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(Category.valueOf(expenseDto.getCategory().toUpperCase()));
        expense.setDate(LocalDate.parse(expenseDto.getDate()));
        expense.setNotes(expenseDto.getNotes());

        return ExpenseMapper.toDto(expenseRepository.save(expense));
    }
}
