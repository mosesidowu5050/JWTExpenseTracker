package com.mosesidowu.expenseSecurity.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {

    private String userId;
    private String expenseTitle;
    private String expenseDescription;
    private double expenseAmount;
    private String category;
    private LocalDate expenseDate;
    private String currencyCode;
}
