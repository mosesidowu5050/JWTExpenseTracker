package com.mosesidowu.expenseSecurity.services;

import com.mosesidowu.expenseSecurity.data.models.Expense;
import com.mosesidowu.expenseSecurity.data.models.User;
import com.mosesidowu.expenseSecurity.data.repository.ExpensesRepository;
import com.mosesidowu.expenseSecurity.data.repository.UserRepository;
import com.mosesidowu.expenseSecurity.dtos.request.*;
import com.mosesidowu.expenseSecurity.dtos.response.ExpenseResponse;
import com.mosesidowu.expenseSecurity.dtos.response.TotalExpenseResponse;
import com.mosesidowu.expenseSecurity.exception.UserException;
import com.mosesidowu.expenseSecurity.util.AuthUtil;
import com.mosesidowu.expenseSecurity.util.Helper;
import com.mosesidowu.expenseSecurity.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mosesidowu.expenseSecurity.util.Helper.*;
import static com.mosesidowu.expenseSecurity.util.Mapper.toExpense;

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
        User user = getUser();
        Expense expense = toExpense(request, user); // update this method to accept User
        expenseRepository.save(expense);

        return toExpense(expense);
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
    public void deleteExpense(String expenseId) {
        User user = getUser();
        deleteExpenses(expenseId, user.getEmail());
    }


    @Override
    public ExpenseResponse getExpenseById(ExpenseIdRequest request, String email) {
        Expense expense = Helper.getExpenseByIdAndUserId(expenseRepository, request.getExpenseId(), request.getUserId());

        return Mapper.toExpenseResponse(expense, request.getCurrencyCode());
    }


    @Override
    public List<ExpenseResponse> getAllExpenses() {
        List<Expense> expenses = getExpenses();

        return expenses.stream()
                .map(Mapper::toResponse)
                .collect(Collectors.toList());
    }



    @Override
    public List<ExpenseResponse> searchExpensesByTitle(String title) {
        return getSearchExpenses(title);
    }


    @Override
    public List<ExpenseResponse> filterByCategory(String category) {
        validateCategory(category);

        return getExpenseCategory(category);
    }



    @Override
    public List<ExpenseResponse> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        validateFilterDateRange(startDate, endDate);
        return getFilterDateResponse(startDate, endDate);
    }



    @Override
    public TotalExpenseResponse getTotalExpense(String email) {
        User user = getUser();

        TotalExpenseRequest request = new TotalExpenseRequest();
        double total = Helper.getTotalExpenseForUser(expenseRepository, user.getUserId());
        String formatted = Helper.formatAmountWithCurrency(total, request.getCurrencyCode());

        TotalExpenseResponse response = new TotalExpenseResponse();
        response.setTotalAmount(formatted);
        return response;
    }



    public void deleteExpenses(String expenseId, String email) throws UserException {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        if (optionalExpense.isEmpty()) {
            throw new UserException("Expense not found");
        }

        Expense expense = optionalExpense.get();
        expenseRepository.delete(expense);
    }


    private List<Expense> getExpenses() {
        User user = getUser();

        String userId = user.getUserId();
        List<Expense> expenses = expenseRepository.findAllByUserId(userId);
        return expenses;
    }


    private List<ExpenseResponse> getExpenseCategory(String category) {
        User user = getUser();

        return getResponseList(category, user);
    }


    private List<ExpenseResponse> getResponseList(String category, User user) {
        List<Expense> expenses = expenseRepository.findByUserIdAndCategoryContainingIgnoreCase(user.getUserId(), category);
        List<ExpenseResponse> responses = new ArrayList<>();

        for (Expense exp : expenses) {
            responses.add(toExpense(exp));
        }

        return responses;
    }


    private List<ExpenseResponse> getSearchExpenses(String title) {
        User user = getUser();

        List<Expense> expenses = expenseRepository.findByUserIdAndExpenseTitleContainingIgnoreCase(
                user.getUserId(), title);
        List<ExpenseResponse> responses = new ArrayList<>();
        for (Expense exp : expenses) {
            responses.add(toExpense(exp));
        }

        return responses;
    }


    private List<ExpenseResponse> getFilterDateResponse(LocalDate startDate, LocalDate endDate) {
        User user = getUser();
        List<Expense> expenses = expenseRepository.findByUserIdAndExpenseDateBetween(user.getUserId(), startDate, endDate);

        List<ExpenseResponse> responses = new ArrayList<>();
        for (Expense exp : expenses) {
            responses.add(toExpense(exp));
        }

        return responses;
    }

    private User getUser() {
        String email = AuthUtil.getCurrentUserEmail();  // Extract email from JWT
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        return user;
    }
}
