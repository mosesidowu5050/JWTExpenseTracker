package com.mosesidowu.expenseSecurity.services;

import com.mosesidowu.expenseSecurity.data.models.Expense;
import com.mosesidowu.expenseSecurity.dtos.request.*;
import com.mosesidowu.expenseSecurity.dtos.response.ExpenseResponse;
import com.mosesidowu.expenseSecurity.dtos.response.TotalExpenseResponse;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    ExpenseResponse addExpense(ExpenseRequest request, String email);

    ExpenseResponse updateExpense(UpdateExpenseRequest request, String email);

    ExpenseResponse getExpenseById(ExpenseIdRequest request, String email);

    List<ExpenseResponse> getAllExpenses();

    List<ExpenseResponse> searchExpensesByTitle(String title);

    List<ExpenseResponse> filterByCategory(String category);

    List<ExpenseResponse> filterByDateRange(LocalDate startDate, LocalDate endDate);

    TotalExpenseResponse getTotalExpense(String email);

    void deleteExpense(String expenseId, String email);
}


