package com.mosesidowu.expenseSecurity.controller;


import com.mosesidowu.expenseSecurity.data.models.Expense;
import com.mosesidowu.expenseSecurity.dtos.request.*;
import com.mosesidowu.expenseSecurity.dtos.response.ApiResponse;
import com.mosesidowu.expenseSecurity.dtos.response.ExpenseResponse;
import com.mosesidowu.expenseSecurity.dtos.response.TotalExpenseResponse;
import com.mosesidowu.expenseSecurity.exception.UserException;
import com.mosesidowu.expenseSecurity.services.ExpenseService;
import com.mosesidowu.expenseSecurity.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequest request) {
        try {
            String email = AuthUtil.getCurrentUserEmail();
            ExpenseResponse response = expenseService.addExpense(request, email);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateExpense(@RequestBody UpdateExpenseRequest request) {
        try {
            String email = AuthUtil.getCurrentUserEmail();
            ExpenseResponse response = expenseService.updateExpense(request, email);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable("expenseId") String expenseId) {
        try {
            String email = AuthUtil.getCurrentUserEmail();
            expenseService.deleteExpense(expenseId, email);
            return new ResponseEntity<>(new ApiResponse("Deleted successfully", true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getExpenseById(@RequestBody ExpenseIdRequest request) {
        try {
            String email = AuthUtil.getCurrentUserEmail();
            ExpenseResponse response = expenseService.getExpenseById(request, email);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllExpenses() {
        try {
            List<ExpenseResponse> expenses = expenseService.getAllExpenses();
            return new ResponseEntity<>(new ApiResponse(expenses, true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchExpensesByTitle(@RequestParam("title") String title) {
        try {
            List<ExpenseResponse> responses = expenseService.searchExpensesByTitle(title);
            return new ResponseEntity<>(new ApiResponse(responses, true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/filter/category")
    public ResponseEntity<?> filterByCategory(@RequestParam("category") String category) {
        try {
            List<ExpenseResponse> responses = expenseService.filterByCategory(category);
            return new ResponseEntity<>(new ApiResponse(responses, true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/filter/date-range")
    public ResponseEntity<?> filterByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        try {
            List<ExpenseResponse> responses = expenseService.filterByDateRange(startDate, endDate);
            return new ResponseEntity<>(new ApiResponse(responses, true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/total")
    public ResponseEntity<?> getTotalExpense() {
        try {
            String email = AuthUtil.getCurrentUserEmail();
            TotalExpenseResponse response = expenseService.getTotalExpense(email);
            return new ResponseEntity<>(new ApiResponse(response, true), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }
}
