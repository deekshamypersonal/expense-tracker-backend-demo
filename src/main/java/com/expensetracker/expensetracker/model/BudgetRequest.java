package com.expensetracker.expensetracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BudgetRequest {

    private String Category;


    private Double amount;
}
