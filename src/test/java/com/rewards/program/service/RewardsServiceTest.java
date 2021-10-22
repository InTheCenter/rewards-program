package com.rewards.program.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.rewards.program.exception.BusinessException;
import com.rewards.program.model.Customer;
import com.rewards.program.model.CustomerRequest;
import com.rewards.program.model.Transaction;
import com.rewards.program.model.TransactionRequest;
import com.rewards.program.repository.CustomerRepository;
import com.rewards.program.repository.TransactionRepository;

@RunWith(SpringRunner.class)
public class RewardsServiceTest{
	
	private List<Customer> customerList = new ArrayList<>();
	private List<Transaction> transactionList = new ArrayList<>();
	private List<TransactionRequest> transactionReqList = new ArrayList<>();

	@InjectMocks
	private RewardsService rewardsService;
	
    @Mock
    private CustomerRepository customerRepository;

	@Mock
    private TransactionRepository transactionRepository;
    
    @Before
    public void init() {
    	Customer customer1 = new Customer();
    	customer1.setId(1L);
    	customer1.setName("John");
    	Customer customer2 = new Customer();
    	customer2.setId(2L);
    	customer2.setName("George");
    	customerList.add(customer1);
    	customerList.add(customer2);
    	
    	Transaction transaction1 = new Transaction();
    	transaction1.setId(1L);
    	transaction1.setName("Transaction 1");
    	transaction1.setCustomer(customer1);
    	transaction1.setDate("15-07-2021");
    	transaction1.setAmount(200L);
    	Transaction transaction2 = new Transaction();
    	transaction2.setId(2L);
    	transaction2.setName("Transaction 2");
    	transaction2.setCustomer(customer2);
    	transaction2.setDate("15-10-2021");
    	transaction2.setAmount(150L);
    	transactionList.add(transaction1);
    	transactionList.add(transaction2);
    	
    	
    	TransactionRequest transactionReq1 = new TransactionRequest();
    	transactionReq1.setAmount(51L);
    	transactionReq1.setCustId(1L);
    	transactionReq1.setDate("15-10-2021");
    	transactionReq1.setName("transaction 1");
    	TransactionRequest transactionReq2 = new TransactionRequest();
    	transactionReq2.setAmount(200L);
    	transactionReq2.setCustId(2L);
    	transactionReq2.setDate("01-08-2021");
    	transactionReq2.setName("transaction 2");
    	transactionReqList.add(transactionReq1);
    	transactionReqList.add(transactionReq2);
    	
    }
    
    
	@Test
	public void getAllTransactionsTestOk() throws Exception {
		Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);
		assertEquals(rewardsService.getAllTransactions().size(), transactionList.size());

	}
	
	@Test
	public void getAllCustomersTestOk() throws Exception {
		Mockito.when(customerRepository.findAll()).thenReturn(customerList);
		assertEquals(rewardsService.getAllCustomers().size(), customerList.size());

	}
	
	@Test
	public void addTransactionsTestOk() throws Exception {
		Optional<Customer> customer1 =  Optional.of(customerList.get(0));
		Optional<Customer> customer2 =  Optional.of(customerList.get(1));
		Mockito.when(customerRepository.findById(1L)).thenReturn(customer1);
		Mockito.when(customerRepository.findById(2L)).thenReturn(customer2);
		Mockito.when(transactionRepository.saveAll(Mockito.anyList())).thenReturn(transactionList);
		assertEquals(transactionList.size(), rewardsService.addTransactions(transactionReqList).size());
	}
	
	@Test
	public void addTransactionsTesCustomerNotFoundk() throws Exception {
		TransactionRequest transaction = new TransactionRequest();
		transaction.setName("Transaction");
		transaction.setCustId(99L);
		transaction.setDate("10-12-2021");
		List<TransactionRequest> transactions = new ArrayList<>();
		transactions.add(transaction);
		assertEquals(0, rewardsService.addTransactions(transactions).size());
	}

	@Test
	public void createCustomerTestOk() throws Exception {
		CustomerRequest customer = new CustomerRequest();
		customer.setId(9L);
		customer.setName("Malcolm");
		Customer customerEntity = new Customer();
		customerEntity.setId(customer.getId());
		customerEntity.setName(customer.getName());
		
		Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customerEntity);
		assertThat(rewardsService.createCustomer(customer)).isEqualTo(customerEntity);
	}
	
	@Test
	public void getRewardsTestOk() {
		Map<String, String> rewardsRequest = new HashMap<>();
		rewardsRequest.put("", "");
		Mockito.when(customerRepository.findAll()).thenReturn(customerList);
		assertThat(rewardsService.getRewards(rewardsRequest)).isNotNull();
		assertThat(rewardsService.getRewards(rewardsRequest).getRewards()).isNotEmpty();
	}
	
	@Test
	public void getRewardsPerMonthTestOk() {
		Map<String, String> rewardsRequest = new HashMap<>();
		rewardsRequest.put("month", "true");
		Mockito.when(customerRepository.findAll()).thenReturn(customerList);
		assertThat(rewardsService.getRewards(rewardsRequest)).isNotNull();
		assertThat(rewardsService.getRewards(rewardsRequest).getRewards()).isNotEmpty();
	}
	
	@Test
	public void  deleteCustomerTestOk(){
		Optional<Customer> customerEntity =  Optional.of(customerList.get(0));
		Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(customerEntity);
    	assertEquals(customerEntity.get().getId(), rewardsService.deleteCustomer("1").getId());
	}
	
	@Test(expected = BusinessException.class)
	public void  deleteCustomerNotFoundTest(){
    	assertThat(rewardsService.deleteCustomer("1")).isNull();;
	}
	
	@Test
	public void  updateCustomerTestOk(){
		Optional<Customer> customerEntity =  Optional.of(customerList.get(0));
		Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(customerEntity);
    	assertEquals("Bruno", rewardsService.updateCustomer("1", "Bruno").getName());
	}
	
	@Test(expected = BusinessException.class)
	public void  updateCustomerNotFoundTest(){
    	assertThat(rewardsService.updateCustomer("1", "Bruno")).isNull();;
	}
	
	@Test
	public void  deleteTransactionTestOk(){
		Optional<Transaction> transactionEntity =  Optional.of(transactionList.get(0));
		Mockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(transactionEntity);
    	assertEquals(transactionEntity.get().getId(), rewardsService.deleteTransaction("1").getId());
	}
	
	@Test(expected = BusinessException.class)
	public void  deleteTransactionNotFoundTest(){
    	assertThat(rewardsService.deleteTransaction("1")).isNull();
	}
	
	@Test
	public void  deleteAllCustomerOKTest(){
    	assertThat(rewardsService.deleteAllCustomers()).isNotNull();
	}
	
	@Test(expected = BusinessException.class)
	public void  deleteAllCustomerExceptionTest(){
		Mockito.doThrow(new ArrayIndexOutOfBoundsException()).when(customerRepository).deleteAll();
		assertThat(rewardsService.deleteAllCustomers()).isNull();
	}
	
	@Test
	public void  updateTransactiontOk(){
		Optional<Transaction> transactionEntity =  Optional.of(transactionList.get(0));
		Mockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(transactionEntity);
    	assertEquals("Transaction updated", rewardsService.updateTransaction("1", "Transaction updated", "21-20-2021", "500").getName());
	}
	
	@Test(expected = BusinessException.class)
	public void  updateTransactionNotFound(){
    	assertThat(rewardsService.updateTransaction("1", "Transaction updated", "21-20-2021", "500")).isNull();;
	}
	

}
