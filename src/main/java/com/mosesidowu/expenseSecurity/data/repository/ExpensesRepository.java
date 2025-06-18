package com.mosesidowu.expenseSecurity.data.repository;

import com.mosesidowu.expenseSecurity.data.models.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpensesRepository extends MongoRepository<Expense,String> {

    List<Expense> findByUserId(String userId);

    Optional<Expense> findByExpenseIdAndUserId(String expenseId, String userId);

    List<Expense> findByUserIdAndExpenseTitleContainingIgnoreCase(String userId, String expenseTitle);

    List<Expense> findByUserIdAndCategoryContainingIgnoreCase(String userId, String category);

    List<Expense> findByUserIdAndExpenseDateBetween(String userId, LocalDate start, LocalDate end);
    List<Expense> findAllByUserId(String userId);


}
