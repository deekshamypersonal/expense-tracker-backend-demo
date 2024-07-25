package com.expensetracker.expensetracker.expenseservice;

import com.expensetracker.expensetracker.model.BudgetRequest;
import com.expensetracker.expensetracker.model.BudgetResponse;
import com.expensetracker.expensetracker.model.ExpenseRequest;
import com.expensetracker.expensetracker.model.ExpenseResponse;

import java.util.List;
import java.util.Map;

public interface ExpenseService {

    void setExpense(ExpenseRequest expenseRequest);
    public Map<String, Double> getGroupedExpense();
    public void deleteExpense(Long id);

    public void setBudget(BudgetRequest budgetRequest);
    public List<BudgetResponse> getBudgets();
    public void deleteBudget(Long id);
    List<ExpenseResponse> getExpense();
    Double getTotal();
}
