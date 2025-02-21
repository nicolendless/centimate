package com.example.expense_tracker.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.expense_tracker.entities.Category;

@Service
public class CategoryService {

    public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }
}
