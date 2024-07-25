package com.expensetracker.expensetracker.controller;

import com.expensetracker.expensetracker.expenseservice.ExpenseService;
import com.expensetracker.expensetracker.io.entity.Budget;
import com.expensetracker.expensetracker.io.entity.Expense;
import com.expensetracker.expensetracker.io.entity.User;
import com.expensetracker.expensetracker.io.repository.BudgetRepository;
import com.expensetracker.expensetracker.io.repository.ExpenseRepository;
import com.expensetracker.expensetracker.io.repository.UserRepository;
import com.expensetracker.expensetracker.model.*;
import com.expensetracker.expensetracker.user.service.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Controller {

    @Autowired
    ExpenseService expenseService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @PostMapping(path = "/register")
    ResponseEntity<String> registerUser(@RequestBody UserRegister userRegister) {
//        logger.info("Method registerUser invoked for user");
        String message = userServiceImpl.registerUser(userRegister);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
    }




    @PostMapping(path = "/setExpense")
    void setExpense(@RequestBody ExpenseRequest expenseRequest) {
//        logger.info("Method registerUser invoked for user");

        expenseService.setExpense(expenseRequest);

//        ModelMapper modelMapper = new ModelMapper();
//        com.expensetracker.expensetracker.io.entity.Expense expenseEntity = modelMapper.map(expenseRequest, com.expensetracker.expensetracker.io.entity.Expense.class);
//       // com.expensetracker.expensetracker.io.entity.Expense expenseEntity=new com.expensetracker.expensetracker.io.entity.Expense();
////        expenseEntity.setAmount(expense.g);
//
//
//        expenseEntity.setDate(LocalDate.now());
//
//        String currentPrincipalName = getLoggedInUser();
//        User user = userRepository.findByEmail(currentPrincipalName);
//        User currentUser = new User();
//        currentUser.setUser_id(user.getUser_id());
//        expenseEntity.setUser(currentUser);
//        expenseEntity.setEmail(currentPrincipalName);
//        expenseRepository.save(expenseEntity);
//        Test m=new Test();
//        m.name=getLoggedInUser();

       // return m;
    }

    @GetMapping("/getGroupedExpense")
    public Map<String, Double> getGroupedExpense() {

        return expenseService.getGroupedExpense();
//        String currentPrincipalName = getLoggedInUser();
//        List<Object[]> results = expenseRepository.findExpenseGroupedByCategory(currentPrincipalName);
//        Map<String, Double> groupedExpenses = new HashMap<>();
//        for (Object[] result : results) {
//            groupedExpenses.put((String) result[0], (Double) result[1]);
//        }
//        return groupedExpenses;
    }

    @DeleteMapping("/deleteExpense/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
//        expenseRepository.deleteById(id)
//        ;
    }

    @PostMapping("/setBudget")
    public void setBudget(@RequestBody BudgetRequest budgetRequest) {

        expenseService.setBudget(budgetRequest);
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
    }

    @GetMapping("/getBudgets")
    public List<BudgetResponse> getBudgets() {

        return expenseService.getBudgets();
//        String email = getLoggedInUser(); // Assuming you have a method to get the current user's email
//        List<Budget> budgets=budgetRepository.findByEmail(email);
//        return budgets.stream()
//                .map(this::convertToDto1)
//                .collect(Collectors.toList());
//

    }



    @DeleteMapping("/deleteBudget/{id}")
    public void deleteBudget(@PathVariable Long id) {

        expenseService.deleteBudget(id);

//        budgetRepository.deleteById(id);
    }






    @GetMapping(path = "/getExpense")
    List<ExpenseResponse> getExpense() {

        return expenseService.getExpense();
//        logger.info("Method registerUser invoked for user");

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
//


        // return m;
    }

    @GetMapping(path = "/getTotal")
    Double getTotal() {

        return expenseService.getTotal();
//        logger.info("Method registerUser invoked for user");
//        return expenseRepository.findTotalExpenseForCurrentMonthByemail(getLoggedInUser());
//


        // return m;
    }

//    private ExpenseResponse convertToDto(Expense expense) {
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(expense, ExpenseResponse.class);
//    }



    @GetMapping(path = "/test1")
    Test test1() {
//        logger.info("Method registerUser invoked for user");
        Test m=new Test();
        m.name="xyz";
        return m;
    }



    private static String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }




}
