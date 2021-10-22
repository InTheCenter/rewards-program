package com.rewards.program.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.program.RewardsApplication;
import com.rewards.program.model.Customer;
import com.rewards.program.model.TransactionRequest;
import com.rewards.program.service.RewardsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RewardsController.class)
@ContextConfiguration(classes = { RewardsApplication.class })
public class RewardsControllerTest {

 	@MockBean
 	private RewardsService rewardsService;
 
	@Autowired
	private MockMvc mvc;

 	private Customer customer = new Customer();

	private TransactionRequest transaction = new TransactionRequest();

	@Test
	public void testJsonType() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/transaction")
			.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void createCustomerOk() throws Exception {
		customer.setName("Oscar");
		mvc.perform(MockMvcRequestBuilders.post("/customer")
				.content(new ObjectMapper().writeValueAsString(customer))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void deleteCustomerOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/customer/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteAllCustomersOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/customer")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateCustomerOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/customer/{id}", "1")
				.param("name", "Transaction updated")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateCustomerBadRequestId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/customer/{id}", " ")
				.param("name", "Transaction updated")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateCustomerBadRequestName() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/customer/{id}", "1")
				.param("name", "")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void getCustomerOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/customer")
			.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
	}

	@Test
	public void addTransactionOk() throws Exception {
		List<TransactionRequest> transactions = new ArrayList<>();
		transaction.setAmount(150L);
		transaction.setDate("10-09-2021");
		transaction.setName("transaction 1");
		transaction.setCustId(1L);
		transactions.add(transaction);

		mvc.perform(MockMvcRequestBuilders.post("/transaction")
				.content(new ObjectMapper().writeValueAsString(transactions))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isCreated());
	}

	@Test
	public void addTransactionBadRequestNullDate() throws Exception {
		List<TransactionRequest> transactions = new ArrayList<>();
		transaction.setAmount(150L);
		transaction.setDate(null);
		transaction.setName("transaction 1");
		transaction.setCustId(1L);
		transactions.add(transaction);

		mvc.perform(MockMvcRequestBuilders.post("/transaction")
				.content(new ObjectMapper().writeValueAsString(transactions))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteTransactionOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/transaction/{id}", "1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteAllTransactionsOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/transaction")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateTransactionOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/transaction/{id}", "1")
				.param("name", "Transaction updated")
				.param("date", "15-10-2021")
				.param("amount", "2500")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateTransactionBadRequest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/transaction/{id}", "1")
				.param("name", "Transaction updated")
				.param("amount", "2500")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void getRewardsPerMonth() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/reward")
				.param("month", "true")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void getRewardsTotal() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/reward")				
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
	}

}
