package com.expensetracker.expensetracker.io.repository;

import com.expensetracker.expensetracker.io.entity.Budget;
import com.expensetracker.expensetracker.io.entity.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends CrudRepository<Budget,Long> {

    @Query("SELECT b FROM Budget b WHERE b.Category = :category AND b.email = :email")
    Budget findByCategoryAndEmail(@Param("category") String category, @Param("email") String email);

    List<Budget> findByEmail(String email);




}
