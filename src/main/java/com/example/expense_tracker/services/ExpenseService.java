package com.example.expense_tracker.services;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ExpenseDto> getExpenses(String title, String category, Pageable pageable) {
        Page<Expense> expenses;

        if ((title != null && !title.isBlank()) && (category != null && !category.isBlank())) {
            expenses = expenseRepository.findByTitleContainingIgnoreCaseAndCategory(title, Category.valueOf(category.toUpperCase()), pageable);
        } else if (title != null && !title.isBlank()) {
            expenses = expenseRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (category != null && !category.isBlank()) {
            expenses = expenseRepository.findByCategory(Category.valueOf(category.toUpperCase()), pageable);
        } else {
            expenses = expenseRepository.findAll(pageable);
        }

        return expenses.map(ExpenseMapper::toDto);
    }

    public ExpenseDto getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseNotFoundException(id));
        return ExpenseMapper.toDto(expense);
    }

    public ExpenseDto createExpense(ExpenseDto expenseDto) {

        Expense expense = ExpenseMapper.toEntity(expenseDto);
        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseMapper.toDto(savedExpense);
    }

    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseNotFoundException(id));
        
        expense.setTitle(expenseDto.getTitle());
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(Category.valueOf(expenseDto.getCategory().toUpperCase()));
        expense.setDate(LocalDate.parse(expenseDto.getDate()));
        expense.setNotes(expenseDto.getNotes());

        Expense updatedExpense = expenseRepository.save(expense);
        return ExpenseMapper.toDto(updatedExpense);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException(id);
        }
        expenseRepository.deleteById(id);
    }
}