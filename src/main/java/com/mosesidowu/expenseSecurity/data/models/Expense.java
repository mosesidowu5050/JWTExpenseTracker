package com.mosesidowu.expenseSecurity.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
public class Expense {

    @Id
    private String expenseId;
    private String userId;
    private String expenseTitle;
    private String expenseDescription;
    private double expenseAmount;
    private String category;
    private LocalDate expenseDate;

}
