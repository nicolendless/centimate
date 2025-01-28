package com.example.expense_tracker.exceptions;

public class ExpenseNotFoundException extends RuntimeException {
    
    public ExpenseNotFoundException(Long id) {
        super("Could not find expense " + id);
    }
}
