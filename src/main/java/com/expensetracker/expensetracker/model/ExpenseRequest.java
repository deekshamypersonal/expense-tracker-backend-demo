package com.expensetracker.expensetracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseRequest {

    private String Description;

    private String category;


    private Double amount;
}
