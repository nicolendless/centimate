package com.example.expense_tracker.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expense_tracker.entities.Category;
import com.example.expense_tracker.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Expense> findByCategory(Category category, Pageable pageable);
    Page<Expense> findByTitleContainingIgnoreCaseAndCategory(String title, Category category, Pageable pageable);
}
