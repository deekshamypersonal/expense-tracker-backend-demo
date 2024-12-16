//package com.expensetracker.expensetracker.expenseservice;
//
//import com.expensetracker.expensetracker.io.entity.Budget;
//import com.expensetracker.expensetracker.io.entity.Expense;
//import com.expensetracker.expensetracker.io.entity.User;
//import com.expensetracker.expensetracker.io.repository.BudgetRepository;
//import com.expensetracker.expensetracker.io.repository.ExpenseRepository;
//import com.expensetracker.expensetracker.io.repository.UserRepository;
//import com.expensetracker.expensetracker.model.BudgetRequest;
//import com.expensetracker.expensetracker.model.BudgetResponse;
//import com.expensetracker.expensetracker.model.ExpenseRequest;
//import com.expensetracker.expensetracker.model.ExpenseResponse;
//import com.expensetracker.expensetracker.user.service.impl.UserServiceImpl;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class ExpenseServiceImpl implements ExpenseService{
//
//
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    BudgetRepository budgetRepository;
//
//    @Autowired
//    ExpenseRepository expenseRepository;
//    @Override
//    public void setExpense(ExpenseRequest expenseRequest) {
//        ModelMapper modelMapper = new ModelMapper();
//        com.expensetracker.expensetracker.io.entity.Expense expenseEntity = modelMapper.map(expenseRequest, com.expensetracker.expensetracker.io.entity.Expense.class);
//        expenseEntity.setDate(LocalDate.now());
//        String currentPrincipalName = getLoggedInUser();
//        User user = userRepository.findByEmail(currentPrincipalName);
//        User currentUser = new User();
//        currentUser.setUser_id(user.getUser_id());
//        expenseEntity.setUser(currentUser);
//        expenseEntity.setEmail(currentPrincipalName);
//        expenseRepository.save(expenseEntity);
//    }
//
//    @Override
//    public Map<String, Double> getGroupedExpense() {
//        String currentPrincipalName = getLoggedInUser();
//        List<Object[]> results = expenseRepository.findExpenseGroupedByCategory(currentPrincipalName);
//        Map<String, Double> groupedExpenses = new HashMap<>();
//        for (Object[] result : results) {
//            groupedExpenses.put((String) result[0], (Double) result[1]);
//        }
//        return groupedExpenses;
//    }
//
//    @Override
//    public void deleteExpense(Long id) {
//
//        expenseRepository.deleteById(id);
//
//    }
//
//    @Override
//    public void setBudget(BudgetRequest budgetRequest) {
//
//        String email = getLoggedInUser(); // Assuming you have a method to get the current user's email
//        Budget existingBudget = budgetRepository.findByCategoryAndEmail(budgetRequest.getCategory(), email);
//        if (existingBudget != null) {
//            existingBudget.setAmount(budgetRequest.getAmount());
//            budgetRepository.save(existingBudget);
//        } else {
//            Budget newBudget = new Budget();
//            newBudget.setCategory(budgetRequest.getCategory());
//            newBudget.setAmount(budgetRequest.getAmount());
//            newBudget.setEmail(email);// Assuming you have a method to get the current user
//            budgetRepository.save(newBudget);
//        }
//
//    }
//
//    @Override
//    public List<BudgetResponse> getBudgets() {
//        String email = getLoggedInUser(); // Assuming you have a method to get the current user's email
//        List<Budget> budgets=budgetRepository.findByEmail(email);
//        return budgets.stream()
//                .map(this::convertToDto1)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteBudget(Long id) {
//        budgetRepository.deleteById(id);
//    }
//
//    @Override
//    public List<ExpenseResponse> getExpense() {
//        String currentPrincipalName = getLoggedInUser();
////        User user = userRepository.findByEmail(currentPrincipalName);
//
//
//        // Fetch expenses for the current user
//        List<Expense> expenseList = expenseRepository.findByEmail(currentPrincipalName);
//
//
//
//        return expenseList.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Double getTotal() {
//        return expenseRepository.findTotalExpenseForCurrentMonthByemail(getLoggedInUser());
//    }
//
//    private static String getLoggedInUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();
//    }
//
//    private BudgetResponse convertToDto1(Budget budget) {
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(budget, BudgetResponse.class);
//    }
//
//    private ExpenseResponse convertToDto(Expense expense) {
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(expense, ExpenseResponse.class);
//    }
//}


package com.expensetracker.expensetracker.expenseservice;

import com.expensetracker.expensetracker.exception.BudgetNotFoundException;
import com.expensetracker.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.expensetracker.exception.UserNotFoundException;
import com.expensetracker.expensetracker.io.entity.Budget;
import com.expensetracker.expensetracker.io.entity.Expense;
import com.expensetracker.expensetracker.io.entity.User;
import com.expensetracker.expensetracker.io.repository.BudgetRepository;
import com.expensetracker.expensetracker.io.repository.ExpenseRepository;
import com.expensetracker.expensetracker.io.repository.UserRepository;
import com.expensetracker.expensetracker.model.BudgetRequest;
import com.expensetracker.expensetracker.model.BudgetResponse;
import com.expensetracker.expensetracker.model.ExpenseRequest;
import com.expensetracker.expensetracker.model.ExpenseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void setExpense(ExpenseRequest expenseRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Expense expenseEntity = modelMapper.map(expenseRequest, Expense.class);
        expenseEntity.setDate(LocalDate.now());
        String currentPrincipalName = getLoggedInUser();
        User user = userRepository.findByEmail(currentPrincipalName);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + currentPrincipalName);
        }
        expenseEntity.setUser(user);
        expenseEntity.setEmail(currentPrincipalName);
        expenseRepository.save(expenseEntity);
    }

    @Override
    public Map<String, Double> getGroupedExpense() {
        String currentPrincipalName = getLoggedInUser();
        List<Object[]> results = expenseRepository.findExpenseGroupedByCategory(currentPrincipalName);
        Map<String, Double> groupedExpenses = new HashMap<>();
        for (Object[] result : results) {
            groupedExpenses.put((String) result[0], (Double) result[1]);
        }
        return groupedExpenses;
    }

    @Override
    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }

    @Override
    public void setBudget(BudgetRequest budgetRequest) {
        String email = getLoggedInUser();
        Budget existingBudget = budgetRepository.findByCategoryAndEmail(budgetRequest.getCategory(), email);
        if (existingBudget != null) {
            existingBudget.setAmount(budgetRequest.getAmount());
            budgetRepository.save(existingBudget);
        } else {
            Budget newBudget = new Budget();
            newBudget.setCategory(budgetRequest.getCategory());
            newBudget.setAmount(budgetRequest.getAmount());
            newBudget.setEmail(email);
            budgetRepository.save(newBudget);
        }
    }

    @Override
    public List<BudgetResponse> getBudgets() {
        String email = getLoggedInUser();
        List<Budget> budgets = budgetRepository.findByEmail(email);
//        if (budgets.isEmpty()) {
//            throw new BudgetNotFoundException("No budgets found for user: " + email);
//        }
        return budgets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new BudgetNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }

    @Override
    public List<ExpenseResponse> getExpense() {
        String currentPrincipalName = getLoggedInUser();
        List<Expense> expenseList = expenseRepository.findByEmailForCurrentMonthAndYear(currentPrincipalName);
//        if (expenseList.isEmpty()) {
//            throw new ExpenseNotFoundException("No expenses found for user: " + currentPrincipalName);
//        }
        return expenseList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotal() {
//        double x=0.0;
//        try {
//            x= expenseRepository.findTotalExpenseForCurrentMonthByemail(getLoggedInUser());
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }
        Double totalExpense = expenseRepository.findTotalExpenseForCurrentMonthByemail(getLoggedInUser());
        //return totalExpense != null ? totalExpense : 0.0;
        totalExpense = totalExpense != null ? totalExpense : 0.0;

        return Double.valueOf(String.format("%.2f", totalExpense));
       // return x;
    }

    private static String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private BudgetResponse convertToDto(Budget budget) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(budget, BudgetResponse.class);
    }

    private ExpenseResponse convertToDto(Expense expense) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(expense, ExpenseResponse.class);
    }
}

