package com.expensetracker.expensetracker.bill.dto;

import com.expensetracker.expensetracker.model.ExpenseRequest;

public class BillResponse {
    private boolean success;
    private String message;
    private ExpenseRequest expenseRequest;

    public BillResponse() {
    }

    public BillResponse(boolean success, String message, ExpenseRequest expenseRequest) {
        this.success = success;
        this.message = message;
        this.expenseRequest = expenseRequest;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExpenseRequest getExpenseRequest() {
        return expenseRequest;
    }

    public void setExpenseRequest(ExpenseRequest expenseRequest) {
        this.expenseRequest = expenseRequest;
    }
}

