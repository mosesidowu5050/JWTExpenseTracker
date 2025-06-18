package com.mosesidowu.expenseSecurity.dtos.request;

import lombok.Data;

@Data
public class DeleteExpenseRequest {

    private String userId;
    private String expenseId;
}
