package com.expensetracker.expensetracker.controller;

import com.expensetracker.expensetracker.expenseservice.ExpenseService;
import com.expensetracker.expensetracker.insights.*;
import com.expensetracker.expensetracker.io.repository.UserRepository;
import com.expensetracker.expensetracker.model.*;
import com.expensetracker.expensetracker.user.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Primary REST controller
 * Public endpoints: /register, /ping, /users/login (handled by filter)
 * Secured endpoints: everything else – require JWT Bearer token
 */
@RestController
public class Controller {

    @Autowired private ExpenseService  expenseService;
    @Autowired private InsightService  insightService;
    @Autowired private UserServiceImpl userServiceImpl;
    @Autowired private UserRepository  userRepository;

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    /**
     * Public – creates a new user account
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegister userRegister) {
        log.info("Register user request for {}", userRegister.getEmail());
        String msg = userServiceImpl.registerUser(userRegister);
        log.info("Register user response: {}", msg);
        return new ResponseEntity<>(msg, new HttpHeaders(), HttpStatus.OK);
    }

    /* ───────────────── EXPENSES ───────────────── */

    /**
     Save a single expense
     */
    @PostMapping("/setExpense")
    public void setExpense(@RequestBody ExpenseRequest expenseRequest) {
        log.info("Set expense: {}", expenseRequest);
        expenseService.setExpense(expenseRequest);
    }

    /**
     * List all expenses for the logged-in user.
     */
    @GetMapping("/getExpense")
    public List<ExpenseResponse> getExpense() {
        log.info("Get all expenses");
        return expenseService.getExpense();
    }

    /**
     * Return total expense grouped by category
     */
    @GetMapping("/getGroupedExpense")
    public Map<String, Double> getGroupedExpense() {
        log.info("Get grouped expense totals");
        return expenseService.getGroupedExpense();
    }

    /**
     * Remove a single expense row by ID.
     */
    @DeleteMapping("/deleteExpense/{id}")
    public void deleteExpense(@PathVariable Long id) {
        log.info("Delete expense id={}", id);
        expenseService.deleteExpense(id);
    }

    /**
     * Return the total of all expenses.
     */
    @GetMapping("/getTotal")
    public Double getTotal() {
        log.info("Get total expenses");
        return expenseService.getTotal();
    }

    /* ───────────────── BUDGETS ───────────────── */

    /**
     * Create or update a budget limit for a specific category.
     */
    @PostMapping("/setBudget")
    public void setBudget(@RequestBody BudgetRequest budgetRequest) {
        log.info("Set budget: {}", budgetRequest);
        expenseService.setBudget(budgetRequest);
    }

    /**
     * List all budget limits configured by the user.
     */
    @GetMapping("/getBudgets")
    public List<BudgetResponse> getBudgets() {
        log.info("Get all budgets");
        return expenseService.getBudgets();
    }

    /**
     * Delete a budget by its primary-key ID.
     */
    @DeleteMapping("/deleteBudget/{id}")
    public void deleteBudget(@PathVariable Long id) {
        log.info("Delete budget id={}", id);
        expenseService.deleteBudget(id);
    }

    /* ───────────────── INSIGHTS ───────────────── */

    /**
     * Return the AI-generated weekly spending insight for the current user.
     */
    @GetMapping("/api/insights")
    public InsightResponse getInsightsForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        log.info("Fetch insight for user {}", email);

        Insight insight = insightService.getInsightForUser(email);
        InsightResponse dto = convertToDto(insight);

        log.info("Insight response: {}", dto);
        return dto;
    }

    /* ───────────────── HEALTH-CHECK ───────────────── */

    @GetMapping("/ping")
    public String ping() {
        log.debug("Ping endpoint hit");
        return "pong";
    }

    /* ───────────────── helper ───────────────── */

    private InsightResponse convertToDto(Insight insight) {
        if (insight == null) return null;
        InsightResponse dto = new InsightResponse();
        dto.setId(insight.getId());
        dto.setInsightText(insight.getInsightText());
        dto.setGeneratedAt(insight.getGeneratedAt());
        return dto;
    }
}