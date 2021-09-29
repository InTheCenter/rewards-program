package com.rewards.program.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TransactionRequest {
    
    @NotNull(message = "custId attribute must not be null")
    private Long custId;
    @NotEmpty(message = "Name attribute must not be empty")
    @NotNull(message = "Name attribute must not be null")
    private String name;
    @NotNull(message = "amount attribute must not be null")
    private Long amount;
    @NotEmpty(message = "date attribute must not be empty")
    @NotNull(message = "date attribute must not be null")
    private String date;

    
    public Long getCustId() {
        return custId;
    }
    public void setCustId(Long custId) {
        this.custId = custId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
            

}
