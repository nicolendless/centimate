package com.example.expense_tracker.mappers;

import java.time.LocalDate;

import com.example.expense_tracker.dtos.ExpenseDto;
import com.example.expense_tracker.entities.Category;
import com.example.expense_tracker.entities.Expense;

public class ExpenseMapper {
    
    public static ExpenseDto toDto(Expense expense) {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setTitle(expense.getTitle());
        expenseDto.setAmount(expense.getAmount());
        expenseDto.setCategory(expense.getCategory().toString());
        expenseDto.setDate(expense.getDate().toString());
        expenseDto.setNotes(expense.getNotes());
        
        return expenseDto;
    }

    public static Expense toEntity(ExpenseDto expenseDto) {
        Expense expense = new Expense();
        expense.setTitle(expenseDto.getTitle());
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(Category.valueOf(expenseDto.getCategory()));
        expense.setDate(LocalDate.parse(expenseDto.getDate()));
        expense.setNotes(expenseDto.getNotes());

        return expense;
    }
}
