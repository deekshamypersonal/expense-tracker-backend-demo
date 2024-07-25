package com.expensetracker.expensetracker.model;

import com.expensetracker.expensetracker.io.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BudgetResponse {

    private long id;


    private String Category;


    private Double amount;

    private String email;

}
