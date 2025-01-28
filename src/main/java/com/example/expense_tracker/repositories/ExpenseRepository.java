package com.example.expense_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expense_tracker.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
