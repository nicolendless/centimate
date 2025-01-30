package com.example.expense_tracker.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.expense_tracker.dtos.ExpenseDto;
import com.example.expense_tracker.entities.Category;
import com.example.expense_tracker.entities.Expense;
import com.example.expense_tracker.exceptions.ExpenseNotFoundException;
import com.example.expense_tracker.services.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    private static final Long EXPENSE_ID = 1L;
    private static final String TITLE = "Test Expense";
    private static final BigDecimal AMOUNT = new BigDecimal("100.00");
    private static final Category CATEGORY = Category.OTHERS;
    private static final LocalDate DATE = LocalDate.of(2025, 1, 1);
    private static final String NOTES = "Test notes";

    private Expense mockExpense;
    private ExpenseDto mockExpenseDto;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
	private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
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
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testGetExpenses_Success() throws Exception {
        Page<ExpenseDto> mockPage = new PageImpl<>(Collections.singletonList(mockExpenseDto));
        when(this.expenseService.getExpenses(any(Pageable.class))).thenReturn(mockPage);
        this.mockMvc.perform(get("/expenses"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].title").value("Test Expense"))
            .andExpect(jsonPath("$.content[0].amount").value(100.00))
            .andExpect(jsonPath("$.content[0].category").value("OTHERS"))
            .andExpect(jsonPath("$.content[0].date").value("2025-01-01"))
            .andExpect(jsonPath("$.content[0].notes").value("Test notes"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testGetExpenses_InvalidQueryParams_Fails() throws Exception {
        this.mockMvc.perform(get("/expenses?page=h&size=10&sort=date&order=desc"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testGetExpenseById_ExpenseNotFound_Fails() throws Exception {
        when(this.expenseService.getExpenseById(any(Long.class)))
            .thenThrow(new ExpenseNotFoundException(EXPENSE_ID));
        this.mockMvc.perform(get("/expenses/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testCreateExpense_Sucess() throws Exception {
        when(this.expenseService.createExpense(any(ExpenseDto.class))).thenReturn(mockExpenseDto);
        this.mockMvc.perform(post("/expenses")
            .contentType("application/json")
            .content(new ObjectMapper().writeValueAsString(mockExpenseDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("Test Expense"))
            .andExpect(jsonPath("$.amount").value(100.00))
            .andExpect(jsonPath("$.category").value("OTHERS"))
            .andExpect(jsonPath("$.date").value("2025-01-01"))
            .andExpect(jsonPath("$.notes").value("Test notes"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testCreateExpense_InvalidData_Fails() throws Exception {
        this.mockMvc.perform(post("/expenses")
            .contentType("application/json")
            .content(new ObjectMapper().writeValueAsString(new ExpenseDto())))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testUpdateExpense_Success() throws Exception {
        when(this.expenseService.updateExpense(any(Long.class), any(ExpenseDto.class))).thenReturn(mockExpenseDto);
        this.mockMvc.perform(put("/expenses/1")
            .contentType("application/json")
            .content(new ObjectMapper().writeValueAsString(mockExpenseDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("Test Expense"))
            .andExpect(jsonPath("$.amount").value(100.00))
            .andExpect(jsonPath("$.category").value("OTHERS"))
            .andExpect(jsonPath("$.date").value("2025-01-01"))
            .andExpect(jsonPath("$.notes").value("Test notes"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testUpdateExpense_InvalidData_Fails() throws Exception {
        this.mockMvc.perform(put("/expenses/1")
            .contentType("application/json")
            .content(new ObjectMapper().writeValueAsString(new ExpenseDto())))
            .andExpect(status().isBadRequest());
    }

   @Test
   @WithMockUser(username = "user", password = "password", roles = "USER")
   void testUpdateExpense_ExpenseNotFound_Fails() throws Exception {
       when(this.expenseService.updateExpense(any(Long.class), any(ExpenseDto.class)))
           .thenThrow(new ExpenseNotFoundException(EXPENSE_ID));
       this.mockMvc.perform(put("/expenses/1")
           .contentType("application/json")
           .content(new ObjectMapper().writeValueAsString(mockExpenseDto)))
           .andExpect(status().isNotFound());
   }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testDeleteExpense_Success() throws Exception {
        this.mockMvc.perform(delete("/expenses/1"))
            .andExpect(status().isOk());
    } 
}