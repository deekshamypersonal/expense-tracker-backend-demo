package com.expensetracker.expensetracker.controller;

import com.expensetracker.expensetracker.expenseservice.ExpenseService;
import com.expensetracker.expensetracker.insights.Insight;
import com.expensetracker.expensetracker.insights.InsightResponse;
import com.expensetracker.expensetracker.insights.InsightService;
import com.expensetracker.expensetracker.io.repository.UserRepository;
import com.expensetracker.expensetracker.model.*;
import com.expensetracker.expensetracker.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private InsightService insightService;

    @Autowired
    private UserRepository userRepository;



    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    ExpenseService expenseService;

    @Autowired
    UserServiceImpl userServiceImpl;


    @PostMapping(path = "/register")
    ResponseEntity<String> registerUser(@RequestBody UserRegister userRegister) {
        String message = userServiceImpl.registerUser(userRegister);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping(path = "/setExpense")
    void setExpense(@RequestBody ExpenseRequest expenseRequest) {
        expenseService.setExpense(expenseRequest);


    }

    @GetMapping("/getGroupedExpense")
    public Map<String, Double> getGroupedExpense() {
        return expenseService.getGroupedExpense();
    }

    @DeleteMapping("/deleteExpense/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);

    }

    @PostMapping("/setBudget")
    public void setBudget(@RequestBody BudgetRequest budgetRequest) {

        expenseService.setBudget(budgetRequest);

    }

    @GetMapping("/getBudgets")
    public List<BudgetResponse> getBudgets() {

        return expenseService.getBudgets();

    }

    @DeleteMapping("/deleteBudget/{id}")
    public void deleteBudget(@PathVariable Long id) {
        expenseService.deleteBudget(id);


    }

    @GetMapping("/test")
    public String test() {

        logger.info("Test endpoint accessed.");
        System.out.println("printlog");
        String response = "test";
        logger.info("Test endpoint response: {}", response);
        return response;


    }

    @GetMapping("/test1")
    public String test1() {

        logger.info("Test endpoint accessed.1");
        System.out.println("printlog1");
        String response = "test1";
        logger.info("Test endpoint response: {1}", response);
        return response;


    }

    @GetMapping(path = "/getExpense")
    List<ExpenseResponse> getExpense() {
        return expenseService.getExpense();
    }

    @GetMapping(path = "/getTotal")
    Double getTotal() {
        return expenseService.getTotal();
    }


    @GetMapping("/api/insights")
    public InsightResponse getInsightsForCurrentUser() {
        // 1) Get logged-in user email from security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        // fetch the single insight
        Insight insight = insightService.getInsightForUser(email);
        return convertToDto(insight);
    }


    private InsightResponse convertToDto(Insight insight) {
        if (insight == null) return null; // handle no insight found
        InsightResponse dto = new InsightResponse();
        dto.setId(insight.getId());
        dto.setInsightText(insight.getInsightText());
        dto.setGeneratedAt(insight.getGeneratedAt());
        return dto;
    }

}
