package com.expensetracker.expensetracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseResponse {

    private long id;


    private String Description;


    private LocalDate date;


    private String category;


    private Double amount;
}
