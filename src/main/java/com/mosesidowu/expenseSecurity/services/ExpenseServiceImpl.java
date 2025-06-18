package com.mosesidowu.expenseSecurity.services;

import com.mosesidowu.expenseSecurity.data.models.Expense;
import com.mosesidowu.expenseSecurity.data.repository.ExpensesRepository;
import com.mosesidowu.expenseSecurity.data.repository.UserRepository;
import com.mosesidowu.expenseSecurity.dtos.request.*;
import com.mosesidowu.expenseSecurity.dtos.response.ExpenseResponse;
import com.mosesidowu.expenseSecurity.dtos.response.TotalExpenseResponse;
import com.mosesidowu.expenseSecurity.exception.UserException;
import com.mosesidowu.expenseSecurity.util.Helper;
import com.mosesidowu.expenseSecurity.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpensesRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @Override
    public ExpenseResponse addExpense(ExpenseRequest request, String email) {
        Helper.validateCreateRequest(request);
        Expense expense = Mapper.toExpense(request);
        Expense saved = expenseRepository.save(expense);

        return Mapper.toExpenseResponse(saved, request.getCurrencyCode());
    }

    @Override
    public ExpenseResponse updateExpense(UpdateExpenseRequest request, String email) {
        Helper.validateUpdateRequest(request);

        Expense expense = Helper.getExpenseByIdAndUserId(expenseRepository, request.getExpenseId(), request.getUserId());

        expense.setExpenseTitle(request.getExpenseTitle());
        expense.setExpenseDescription(request.getExpenseDescription());
        expense.setExpenseAmount(request.getExpenseAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        Expense updated = expenseRepository.save(expense);

        return Mapper.toExpenseResponse(updated, request.getCurrencyCode());
    }

    @Override
    public void deleteExpense(String expenseId, String userId) {
        Expense expense = expenseRepository.findByExpenseIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new UserException("Expense not found"));

        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseResponse getExpenseById(ExpenseIdRequest request, String email) {
        Expense expense = Helper.getExpenseByIdAndUserId(expenseRepository, request.getExpenseId(), request.getUserId());
        return Mapper.toExpenseResponse(expense, request.getCurrencyCode());
    }

    @Override
    public List<ExpenseResponse> getAllExpenses(String userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        List<ExpenseResponse> responses = new ArrayList<>();

        for (Expense exp : expenses) {
            responses.add(Mapper.toExpense(exp));
        }

        return responses;
    }


    @Override
    public List<ExpenseResponse> searchExpensesByTitle(String userId, String title) {
        List<Expense> expenses = expenseRepository.findByUserIdAndExpenseTitleContainingIgnoreCase(userId, title);
        List<ExpenseResponse> responses = new ArrayList<>();

        for (Expense exp : expenses) {
            responses.add(Mapper.toExpense(exp));
        }

        return responses;
    }



    @Override
    public List<ExpenseResponse> filterByCategory(String userId, String category) {
        List<Expense> expenses = expenseRepository.findByUserIdAndCategory(userId, category);
        List<ExpenseResponse> responses = new ArrayList<>();

        for (Expense exp : expenses) {
            responses.add(Mapper.toExpense(exp));
        }

        return responses;
    }



    @Override
    public List<ExpenseResponse> filterByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepository.findByUserIdAndExpenseDateBetween(userId, startDate, endDate);

        List<ExpenseResponse> responses = new ArrayList<>();
        for (Expense exp : expenses) {
            responses.add(Mapper.toExpense(exp));
        }

        return responses;
    }



    @Override
    public TotalExpenseResponse getTotalExpense(String email) {
        TotalExpenseRequest request = new TotalExpenseRequest();
        double total = Helper.getTotalExpenseForUser(expenseRepository, email);
        String formatted = Helper.formatAmountWithCurrency(total, request.getCurrencyCode());

        TotalExpenseResponse response = new TotalExpenseResponse();
        response.setTotalAmount(formatted);
        return response;
    }
}
