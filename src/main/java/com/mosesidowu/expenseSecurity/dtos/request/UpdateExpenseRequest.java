package com.mosesidowu.expenseSecurity.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateExpenseRequest {

    private String userId;
    private String expenseId;
    private String expenseTitle;
    private String expenseDescription;
    private String category;
    private double expenseAmount;
    private LocalDate expenseDate;
    private String currencyCode;
}
