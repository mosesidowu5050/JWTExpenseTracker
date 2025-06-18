package com.mosesidowu.expenseSecurity.services;

import com.mosesidowu.expenseSecurity.dtos.request.*;
import com.mosesidowu.expenseSecurity.dtos.response.ExpenseResponse;
import com.mosesidowu.expenseSecurity.dtos.response.TotalExpenseResponse;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    ExpenseResponse addExpense(ExpenseRequest request, String email);

    ExpenseResponse updateExpense(UpdateExpenseRequest request, String email);

    ExpenseResponse getExpenseById(ExpenseIdRequest request, String email);

    public List<ExpenseResponse> getAllExpenses(String email);

    List<ExpenseResponse> searchExpensesByTitle(String email, String title);

    List<ExpenseResponse> filterByCategory(String email, String category);

    List<ExpenseResponse> filterByDateRange(String email, LocalDate startDate, LocalDate endDate);

    TotalExpenseResponse getTotalExpense(String email);

    void deleteExpense(String expenseId, String email);
}


