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
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class Controller {

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

    @GetMapping(path = "/getExpense")
    List<ExpenseResponse> getExpense() {
        return expenseService.getExpense();
    }

    @GetMapping(path = "/getTotal")
    Double getTotal() {
        return expenseService.getTotal();
    }

}
