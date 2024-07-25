package com.expensetracker.expensetracker.io.repository;

import com.expensetracker.expensetracker.io.entity.Expense;
import com.expensetracker.expensetracker.io.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense,Long> {

    List<Expense> findByEmail(String email);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.email = :email AND MONTH(e.date) = MONTH(CURRENT_DATE) AND YEAR(e.date) = YEAR(CURRENT_DATE)")
    Double findTotalExpenseForCurrentMonthByemail(@Param("email") String email);

    @Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE e.email = :email GROUP BY e.category")
    List<Object[]> findExpenseGroupedByCategory(@Param("email") String email);
}
