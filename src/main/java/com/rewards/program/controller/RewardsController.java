package com.rewards.program.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

import com.rewards.program.model.Customer;
import com.rewards.program.model.CustomerRequest;
import com.rewards.program.model.RewardsResponse;
import com.rewards.program.model.Transaction;
import com.rewards.program.model.TransactionRequest;
import com.rewards.program.service.RewardsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@RestController
@Api(tags = "RewardsController")
public class RewardsController {

	@Autowired
	private RewardsService service;

	private static Logger log = LogManager.getLogger(RewardsController.class);

	@GetMapping(path="/reward",produces="application/json")
	@ApiOperation(value="Get all rewards", notes="Get all rewards")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	public ResponseEntity<RewardsResponse> getRewards(@RequestParam(required = false) Map<String, String> params) {
		log.info("[getRewards]");
		return new ResponseEntity<>(this.service.getRewards(params), HttpStatus.OK);
	}
	
	@ApiOperation(value="Get all customers", notes="Get all customers")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@GetMapping(path = "/customer", produces = "application/json")
	public ResponseEntity<List<Customer>> getCustomers(@RequestParam(required = false) Map<String, String> params) {
		log.info("[getCustomers]");
		return new ResponseEntity<>(service.getAllCustomers(), HttpStatus.OK);
	}

	@ApiOperation(value="Post customer", notes="Create a new customer")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@PostMapping(path = "/customer", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Customer> postCustomer(@Valid @RequestBody CustomerRequest request) {
		log.info("[postCustomer], request: {}", request);
		return new ResponseEntity<>(this.service.createCustomer(request), HttpStatus.CREATED);
	}

	@ApiOperation(value="Delete a customer", notes="Delete a customer by id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@DeleteMapping(path = "/customer/{id}", produces = "application/json")
	public ResponseEntity<Customer> deleteCustomer(@Valid @PathVariable @NotBlank String id) {
		log.info("[deleteCustomer], id: {}", id);
		return new ResponseEntity<>(this.service.deleteCustomer(id), HttpStatus.OK);
	}

	@ApiOperation(value="Delete all customers", notes="Delete all customers")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@DeleteMapping(path = "/customer", produces = "application/json")
	public ResponseEntity<String> deleteAllCustomers() {
		log.info("[deleteAllCustomers]");
		return new ResponseEntity<>(this.service.deleteAllCustomers(), HttpStatus.OK);
	}

	@ApiOperation(value="Update a customer", notes="Update a customer by id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@PutMapping(path = "/customer/{id}", produces = "application/json")
	public ResponseEntity<Customer> updateCustomer(@Valid @PathVariable @NotBlank String id,
			@RequestParam(value = "name", required = true) @NotBlank String name) {
		log.info("[updateCustomer], id: {}, name: {}", id, name);
		return new ResponseEntity<>(this.service.updateCustomer(id, name), HttpStatus.OK);
	}

	@ApiOperation(value="Get all transactions", notes="Get all transactions")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@GetMapping(path = "/transaction", produces = "application/json")
	public ResponseEntity<List<Transaction>> getTransactions(
			@RequestParam(required = false) Map<String, String> params) {
		log.info("[getTransactions]");
		return new ResponseEntity<>(this.service.getAllTransactions(), HttpStatus.OK);
	}

	@ApiOperation(value="Add transacctions", notes="Add transactions by a list")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@PostMapping(path = "/transaction", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Transaction>> postTransaction(@RequestBody List<@Valid TransactionRequest> request) {
		log.info("[postTransaction], request: {}", request);
		return new ResponseEntity<>(this.service.addTransactions(request), HttpStatus.CREATED);
	}

	@ApiOperation(value="Delete a transaction", notes="Delete a transaction by id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@DeleteMapping(path = "/transaction/{id}", produces = "application/json")
	public ResponseEntity<Transaction> deleteTransaction(@Valid @PathVariable @NotBlank String id) {
		log.info("[deleteTransaction]], id: {}", id);
		return new ResponseEntity<>(this.service.deleteTransaction(id), HttpStatus.OK);
	}

	@ApiOperation(value="Delete all transactions", notes="Delete all transactions")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@DeleteMapping(path = "/transaction", produces = "application/json")
	public ResponseEntity<String> deleteAllTransactions() {
		log.info("[deleteAllTransactions]");
		return new ResponseEntity<>(this.service.deleteAllTransactions(), HttpStatus.OK);
	}
	
	@ApiOperation(value="Update a transaction", notes="Update a transaction by id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 405, message = "Method not allowed"),
			@ApiResponse(code = 500, message = "Internar Server Error")})
	@PutMapping(path = "/transaction/{id}", produces = "application/json")
	public ResponseEntity<Transaction> updateTransaction(@Valid @PathVariable @NotBlank String id,
			@RequestParam(value = "name", required = true) @NotBlank String name,
			@RequestParam(value = "date", required = true) @NotBlank String date,
			@RequestParam(value = "amount", required = true) @NotBlank String amount) {
		log.info("[updateTransaction], id: {}, name: {}, date: {}, amount: {}", id, name, date, amount);
		return new ResponseEntity<>(this.service.updateTransaction(id, name, date, amount), HttpStatus.OK);
	}

}
