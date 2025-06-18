package com.mosesidowu.expenseSecurity.util;

import com.mosesidowu.expenseSecurity.data.models.Expense;
import com.mosesidowu.expenseSecurity.data.repository.ExpensesRepository;
import com.mosesidowu.expenseSecurity.dtos.request.DeleteExpenseRequest;
import com.mosesidowu.expenseSecurity.dtos.request.ExpenseRequest;
import com.mosesidowu.expenseSecurity.dtos.request.UpdateExpenseRequest;
import com.mosesidowu.expenseSecurity.exception.DateException;
import com.mosesidowu.expenseSecurity.exception.ExpenseException;

import java.time.LocalDate;
import java.util.List;

public class Helper {

    public static void validateCreateRequest(ExpenseRequest request) {
        if (request.getUserId() == null || request.getUserId().isEmpty()) throw new IllegalArgumentException("User ID is required");
        if (request.getExpenseTitle() == null || request.getExpenseTitle().isEmpty()) throw new IllegalArgumentException("Expense title is required");
        if (request.getExpenseAmount() <= 0) throw new IllegalArgumentException("Amount must be greater than 0");
    }

    public static void validateUpdateRequest(UpdateExpenseRequest request) {
        if (request.getExpenseId() == null || request.getExpenseId().isEmpty()) throw new IllegalArgumentException("Expense ID is required");

        validateCreateRequest(new ExpenseRequest(
                request.getUserId(),
                request.getExpenseTitle(),
                request.getExpenseDescription(),
                request.getExpenseAmount(),
                request.getCategory(),
                request.getExpenseDate(),
                request.getCurrencyCode()
        ));
    }

    public static void validateDeleteRequest(DeleteExpenseRequest request) {
        if (request.getExpenseId() == null || request.getExpenseId().isEmpty()) throw new IllegalArgumentException("Expense ID is required");

        if (request.getUserId() == null || request.getUserId().isEmpty()) throw new IllegalArgumentException("User ID is required");
    }

    public static Expense getExpenseByIdAndUserId(ExpensesRepository repository, String expenseId, String userId) {
        return repository.findByExpenseIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new ExpenseException("Expense not found"));
    }

    public static double getTotalExpenseForUser(ExpensesRepository repository, String userId) {
        List<Expense> expenses = repository.findByUserId(userId);
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getExpenseAmount();
        }
        return total;
    }


    public static String formatAmountWithCurrency(double amount, String currencyCode) {
        if (currencyCode == null || currencyCode.isEmpty()) currencyCode = "USD";

        return currencyCode.toUpperCase() + " " + String.format("%.2f", amount);
    }

    public static String formatAmountWithAmount(double amount) {
        return String.format("%.2f", amount);
    }

    public static void validateCategory(String category) {
        if (category == null || category.isEmpty()) throw new ExpenseException("Category is required");
    }

    public static void validateFilterDateRange(LocalDate startDate, LocalDate endDate) {
        if(startDate == null || endDate == null) throw new DateException("Start date and end date is required");
    }
}
