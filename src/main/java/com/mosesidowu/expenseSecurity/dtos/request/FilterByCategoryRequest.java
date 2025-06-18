package com.mosesidowu.expenseSecurity.dtos.request;

import lombok.Data;

@Data
public class FilterByCategoryRequest {

    private String userId;
    private String category;
    private String currencyCode;
}
