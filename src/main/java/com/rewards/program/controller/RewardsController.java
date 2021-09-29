package com.rewards.program.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.rewards.program.model.Customer;
import com.rewards.program.model.CustomerRequest;
import com.rewards.program.model.RewardsResponse;
import com.rewards.program.model.Transaction;
import com.rewards.program.model.TransactionRequest;
import com.rewards.program.service.RewardsService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
public class RewardsController {

	@Autowired
	private RewardsService service;

    private static Logger log = LogManager.getLogger(RewardsController.class);
	
	@GetMapping(path = "/reward", produces = "application/json")
	public ResponseEntity<RewardsResponse> getRewards(
			@RequestParam(required = false) Map<String, String> params) {
		log.info("[getRewards]");
		return new ResponseEntity<>(this.service.getRewards(params), HttpStatus.OK);
	}

	@GetMapping(path = "/customer", produces = "application/json")
	public ResponseEntity<List<Customer>> getCustomers(
			@RequestParam(required = false) Map<String, String> params) {
		log.info("[getCustomers]");
		return new ResponseEntity<>(service.getAllCustomers(), HttpStatus.OK);
	}

	@PostMapping(path = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> postCustomer(@Valid @RequestBody CustomerRequest request) {
    	log.info("[postCustomer], request: {}", request);
    	return new ResponseEntity<>(this.service.createCustomer(request), HttpStatus.CREATED);
    }

	@DeleteMapping(path = "/customer/{id}", produces = "application/json")
    public ResponseEntity<Customer> deleteCustomer(@Valid @PathVariable @NotBlank String id) {
    	log.info("[deleteCustomer], id: {}", id);
    	return new ResponseEntity<>(this.service.deleteCustomer(id), HttpStatus.OK);
    }

	@PutMapping(path = "/customer/{id}", produces = "application/json")
    public ResponseEntity<Customer> updateCustomer(@Valid @PathVariable @NotBlank String id,
		@RequestParam(value = "name", required = true) @NotBlank String name) {
    	log.info("[updateCustomer], id: {}, name: {}", id, name);
    	return new ResponseEntity<>(this.service.updateCustomer(id, name), HttpStatus.OK);
    }

	@GetMapping(path = "/transaction", produces = "application/json")
	public ResponseEntity<List<Transaction>> getTransactions(
			@RequestParam(required = false) Map<String, String> params) {
		log.info("[getTransactions]");
		return new ResponseEntity<>(this.service.getAllTransactions(), HttpStatus.OK);
	}

    @PostMapping(path = "/transaction", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Transaction>> postTransaction(@RequestBody List<@Valid TransactionRequest> request) {
    	log.info("[postTransaction], request: {}", request);
    	return new ResponseEntity<>(this.service.addTransactions(request), HttpStatus.CREATED);
    }

	@DeleteMapping(path = "/transaction/{id}", produces = "application/json")
    public ResponseEntity<Transaction> deleteTransaction(@Valid @PathVariable @NotBlank String id) {
    	log.info("[deleteTransaction]], id: {}", id);
    	return new ResponseEntity<>(this.service.deleteTransaction(id), HttpStatus.OK);
    }

	@PutMapping(path = "/transaction/{id}", produces = "application/json")
    public ResponseEntity<Transaction> updateTransaction(@Valid @PathVariable @NotBlank String id,
		@RequestParam(value = "name", required = true) @NotBlank String name,
		@RequestParam(value = "date", required = true) @NotBlank String date,
		@RequestParam(value = "amount", required = true) @NotBlank String amount) {
    	log.info("[updateTransaction], id: {}, name: {}, date: {}, amount: {}", id, name, date, amount);
    	return new ResponseEntity<>(this.service.updateTransaction(id, name, date, amount), HttpStatus.OK);
    }

}
