package com.example.expense_tracker.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.expense_tracker.dtos.ExpenseDto;
import com.example.expense_tracker.entities.Category;
import com.example.expense_tracker.entities.Expense;
import com.example.expense_tracker.exceptions.ExpenseNotFoundException;
import com.example.expense_tracker.mappers.ExpenseMapper;
import com.example.expense_tracker.repositories.ExpenseRepository;

public class ExpenseServiceTest {

    private static final Long EXPENSE_ID = 1L;
    private static final String TITLE = "Test Expense";
    private static final BigDecimal AMOUNT = new BigDecimal("100.00");
    private static final Category CATEGORY = Category.OTHERS;
    private static final LocalDate DATE = LocalDate.of(2025, 1, 1);
    private static final String NOTES = "Test notes";

    private Expense mockExpense;
    private ExpenseDto mockExpenseDto;
    
    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockExpense = new Expense();        
        mockExpense.setTitle(TITLE);
        mockExpense.setAmount(AMOUNT);
        mockExpense.setCategory(CATEGORY);
        mockExpense.setDate(DATE);
        mockExpense.setNotes(NOTES);

        mockExpenseDto = new ExpenseDto();
        mockExpenseDto.setId(EXPENSE_ID);
        mockExpenseDto.setTitle(TITLE);
        mockExpenseDto.setAmount(AMOUNT);
        mockExpenseDto.setCategory(CATEGORY.toString());
        mockExpenseDto.setDate(DATE.toString());
        mockExpenseDto.setNotes(NOTES);
    }

    @Test
    void testGetExpenses() {        
        Pageable pageable = PageRequest.of(0, 10);

        Page<Expense> expensePage = new PageImpl<>(Collections.singletonList(mockExpense));
        when(expenseRepository.findAll(pageable)).thenReturn(expensePage);

        Page<ExpenseDto> result = expenseService.getExpenses(pageable);
    
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(TITLE, result.getContent().get(0).getTitle());
        assertEquals(AMOUNT, result.getContent().get(0).getAmount());
    
        verify(expenseRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetExpenseById_Success() {
        when(expenseRepository.findById(EXPENSE_ID)).thenReturn(Optional.of(mockExpense));
        ExpenseDto result = expenseService.getExpenseById(EXPENSE_ID);

        assertNotNull(result);
        assertEquals(TITLE, result.getTitle());
        assertEquals(AMOUNT, result.getAmount());
        assertEquals(CATEGORY.toString(), result.getCategory());
        assertEquals(DATE.toString(), result.getDate());
        assertEquals(NOTES, result.getNotes());

        verify(expenseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetExpenseById_ExpenseNotFound() {
        when(expenseRepository.findById(EXPENSE_ID)).thenReturn(Optional.empty());
    
        assertThrows(ExpenseNotFoundException.class, () -> expenseService.getExpenseById(EXPENSE_ID));
    
        verify(expenseRepository, times(1)).findById(EXPENSE_ID);
        verifyNoMoreInteractions(expenseRepository);
    }

    @Test
    void testCreateExpense_Sucess() {        
        when(expenseRepository.save(any(Expense.class))).thenReturn(mockExpense);
        ExpenseDto result = expenseService.createExpense(mockExpenseDto);

        assertNotNull(result);
        assertEquals(TITLE, result.getTitle());
        assertEquals(AMOUNT, result.getAmount());
        assertEquals(CATEGORY.toString(), result.getCategory());
        assertEquals(DATE.toString(), result.getDate());
        assertEquals(NOTES, result.getNotes());

        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testUpdateExpense_Success() {        
        when(expenseRepository.findById(EXPENSE_ID)).thenReturn(Optional.of(mockExpense));
        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ExpenseDto updatedExpenseDto = new ExpenseDto();
        updatedExpenseDto.setId(EXPENSE_ID);
        updatedExpenseDto.setTitle("Updated Title");
        updatedExpenseDto.setAmount(new BigDecimal(200.0));
        updatedExpenseDto.setCategory("CLOTHING");
        updatedExpenseDto.setDate("2025-01-30");
        updatedExpenseDto.setNotes("Updated notes");


        ExpenseDto result = expenseService.updateExpense(EXPENSE_ID, updatedExpenseDto);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals(new BigDecimal(200.0), result.getAmount());
        assertEquals("CLOTHING", result.getCategory());
        assertEquals("2025-01-30", result.getDate());
        assertEquals("Updated notes", result.getNotes());

        verify(expenseRepository).findById(EXPENSE_ID);
        verify(expenseRepository).save(any(Expense.class));
    }

    @Test
    void testUpdateExpense_ExpenseNotFound() {        
        when(expenseRepository.findById(EXPENSE_ID)).thenReturn(Optional.empty());

        ExpenseDto updatedExpenseDto = new ExpenseDto();
        updatedExpenseDto.setId(EXPENSE_ID);
        updatedExpenseDto.setTitle("Updated Title");
        updatedExpenseDto.setAmount(new BigDecimal(200.0));
        updatedExpenseDto.setCategory("CLOTHING");
        updatedExpenseDto.setDate("2025-01-30");
        updatedExpenseDto.setNotes("Updated notes");
        
        assertThrows(ExpenseNotFoundException.class, () -> expenseService.updateExpense(EXPENSE_ID, updatedExpenseDto));
        
        verify(expenseRepository, times(1)).findById(EXPENSE_ID);
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    @Test
    void testDeleteExpense_Success() {        
        when(expenseRepository.existsById(EXPENSE_ID)).thenReturn(true);

        expenseService.deleteExpense(EXPENSE_ID);

        verify(expenseRepository).existsById(EXPENSE_ID);
        verify(expenseRepository).deleteById(EXPENSE_ID);
    }

    @Test
    void testDeleteExpense_ExpenseNotFound() {        
        when(expenseRepository.existsById(EXPENSE_ID)).thenReturn(false);
        
        assertThrows(ExpenseNotFoundException.class, () -> expenseService.deleteExpense(EXPENSE_ID));

        verify(expenseRepository).existsById(EXPENSE_ID);
        verify(expenseRepository, never()).deleteById(anyLong());
    }
}