package com.example.expense_tracker.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense_tracker.dtos.ExpenseDto;
import com.example.expense_tracker.entities.Expense;
import com.example.expense_tracker.exceptions.ExpenseNotFoundException;
import com.example.expense_tracker.repositories.ExpenseRepository;
import com.example.expense_tracker.services.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    
    private ExpenseRepository expenseRepository;
    private ExpenseService expenseService;

    public ExpenseController(ExpenseRepository expenseRepository, ExpenseService expenseService) {
        this.expenseRepository = expenseRepository;
        this.expenseService = expenseService;
    }
    
     /**
     * Retrieves all expenses with pagination.
     *
     * @param page The page number (starting from 0).
     * @param size The number of items per page.
     * @return A paginated response with expense details.
     */
    @GetMapping
    public Page<Expense> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
        PageRequest pageable = PageRequest.of(page, size);
        return expenseRepository.findAll(pageable);
    }

    /**
     * Retrieves a specific expense by its ID.
     *
     * @param id The ID of the expense to retrieve (from the path).
     * @return A JSON response with the expense details or an error message.
     */
    @GetMapping("{id}")
    public Expense getExpense(@PathVariable Long id) {
        return expenseRepository.findById(id)
            .orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    /**
     * Creates a new expense.
     *
     * @param expenseRequest The request body containing the details of the expense to create.
     *                       Must be valid according to the defined constraints.
     * @return The created expense object.     
     */
    @PostMapping
    public ExpenseDto createExpense(@Valid @RequestBody ExpenseDto expenseInput) {
        return expenseService.createExpense(expenseInput);
    }

    /**
     * Updates a new expense.
     *
     * @param id The ID of the expense to update (from the path).
     * @param expenseRequest The request body containing the details of the expense to update.
     *                       Must be valid according to the defined constraints.
     * @return The created expense object.     
     */
    @PutMapping("{id}")
    public ExpenseDto updatExpense(@PathVariable Long id, @Valid @RequestBody ExpenseDto expenseInput) {
        return expenseService.updateExpense(id, expenseInput);
    }
}
