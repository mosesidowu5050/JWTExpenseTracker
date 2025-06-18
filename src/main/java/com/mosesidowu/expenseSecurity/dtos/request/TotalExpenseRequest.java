package com.mosesidowu.expenseSecurity.dtos.request;

import lombok.Data;

@Data
public class TotalExpenseRequest {

    private String userId;
    private String currencyCode;
}
