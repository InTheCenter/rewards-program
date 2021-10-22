package com.rewards.program.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomerRequest {
	@JsonIgnore
    private Long id;
    @NotEmpty(message = "Name attribute must not be empty")
    @NotNull(message = "Name attribute must not be null")
    private String name;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
