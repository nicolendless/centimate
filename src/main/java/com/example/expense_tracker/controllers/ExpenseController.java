package com.example.expense_tracker.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense_tracker.dtos.ExpenseDto;
import com.example.expense_tracker.services.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Retrieves all expenses with pagination.
     *
     * @param page The page number (starting from 0).
     * @param size The number of items per page.
     * @param sortBy The field to sort by.
     * @param sortDir The direction to sort (asc or desc).
     * @return A paginated response with expense details.
     */
    @GetMapping
    public Page<ExpenseDto> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "date") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String sortDir,
            @RequestParam(name = "title", required = false) String title
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return expenseService.getExpenses(title, pageable);
    }

    /**
     * Retrieves a specific expense by its ID.
     *
     * @param id The ID of the expense to retrieve (from the path).
     * @return A JSON response with the expense details or an error message.
     */
    @GetMapping("{id}")
    public ExpenseDto getExpense(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    /**
     * Creates a new expense.
     *
     * @param expenseDto The expense data transfer object.
     * @return The created expense.
     */
    @PostMapping
    public ExpenseDto createExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        return expenseService.createExpense(expenseDto);
    }

    /**
     * Updates an existing expense.
     *
     * @param id The ID of the expense to update.
     * @param expenseDto The updated expense data transfer object.
     * @return The updated expense.
     */
    @PutMapping("{id}")
    public ExpenseDto updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseDto expenseDto) {
        return expenseService.updateExpense(id, expenseDto);
    }

    /**
     * Deletes an expense by its ID.
     *
     * @param id The ID of the expense to delete.
     */
    @DeleteMapping("{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}