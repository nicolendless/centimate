package com.example.expense_tracker.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ExpenseDto {

    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    private String title;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be at least 0.01") 
    @Digits(integer = 12, fraction = 2, message = "Amount must be a valid monetary value")
    private BigDecimal amount;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotBlank(message = "Date is mandatory")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in yyyy-MM-dd format")
    private String date;

    @Size(max = 500, message = "Notes must be at most 500 characters")
    private String notes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
