package com.mosesidowu.expenseSecurity.util;

import com.mosesidowu.expenseSecurity.data.models.Expense;
import com.mosesidowu.expenseSecurity.data.models.User;
import com.mosesidowu.expenseSecurity.dtos.request.ExpenseRequest;
import com.mosesidowu.expenseSecurity.dtos.request.RegisterUserRequest;
import com.mosesidowu.expenseSecurity.dtos.response.ExpenseResponse;
import com.mosesidowu.expenseSecurity.dtos.response.LoginUserResponse;
import com.mosesidowu.expenseSecurity.dtos.response.RegisterUserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Mapper {

    public static User userMapper(PasswordEncoder passwordEncoder, RegisterUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    public static RegisterUserResponse userMapperResponse(User user) {
        RegisterUserResponse response = new RegisterUserResponse();
        response.setId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setMessage("Registration successful");

        return response;
    }

    public static LoginUserResponse userMapper(User user) {
        LoginUserResponse response = new LoginUserResponse();
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setMessage("Login successfully");

        return response;
    }


    public static Expense toExpense(ExpenseRequest request, User user) {
        Expense expense = new Expense();
        expense.setUserId(request.getUserId());
        expense.setExpenseTitle(request.getExpenseTitle());
        expense.setExpenseDescription(request.getExpenseDescription());
        expense.setExpenseAmount(request.getExpenseAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());

        return expense;
    }

    public static ExpenseResponse toExpenseResponse(Expense expense, String currencyCode) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getExpenseId());
        response.setTitle(expense.getExpenseTitle());
        response.setDescription(expense.getExpenseDescription());
        response.setFormattedAmount(Helper.formatAmountWithCurrency(expense.getExpenseAmount(), currencyCode));
        response.setCategory(expense.getCategory());
        response.setCreatedAt(expense.getExpenseDate());
        response.setExpenseAmount(expense.getExpenseAmount());


        return response;
    }

    public static ExpenseResponse toExpense(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getExpenseId());
        response.setTitle(expense.getExpenseTitle());
        response.setDescription(expense.getExpenseDescription());
        response.setCategory(expense.getCategory());
        response.setCreatedAt(expense.getExpenseDate());
        response.setExpenseAmount(expense.getExpenseAmount());
        response.setFormattedAmount(Helper.formatAmountWithAmount(expense.getExpenseAmount()));

        return response;
    }

    public static ExpenseResponse toResponse(Expense expense) {
            ExpenseResponse response = new ExpenseResponse();
            response.setId(expense.getExpenseId());
            response.setTitle(expense.getExpenseTitle());
            response.setDescription(expense.getExpenseDescription());
            response.setExpenseAmount(expense.getExpenseAmount());
            response.setCreatedAt(expense.getExpenseDate());
            response.setCategory(expense.getCategory());
            response.setFormattedAmount(Helper.formatAmountWithAmount(expense.getExpenseAmount()));

            return response;
    }
}
