package com.rewards.program.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.rewards.program.exception.BusinessException;
import com.rewards.program.model.Customer;
import com.rewards.program.model.CustomerRequest;
import com.rewards.program.model.Reward;
import com.rewards.program.model.RewardsResponse;
import com.rewards.program.model.Transaction;
import com.rewards.program.model.TransactionRequest;
import com.rewards.program.repository.CustomerRepository;
import com.rewards.program.repository.TransactionRepository;
import com.rewards.program.util.ErrorEnumUtil;
import com.rewards.program.util.RewardsUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RewardsService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Log de la clase.
     */
    private static Logger log = LogManager.getLogger(RewardsService.class);

    public List<Transaction> getAllTransactions(){
        log.info("[getAllTransactions]");
        return transactionRepository.findAll();
    }

    public List<Customer> getAllCustomers() {
        log.info("[getAllCustomers]");
        return customerRepository.findAll();
    }

    public List<Transaction> addTransactions(List<TransactionRequest> request){
        log.info("[addTransactions] start");

        request.stream().filter(t -> !RewardsUtil.isDateValid(t.getDate())).
            findFirst().ifPresent(p -> {throw new BusinessException(ErrorEnumUtil.DATE_FORMAT_ERROR, p.getDate(), HttpStatus.BAD_REQUEST);});
        
        List<Transaction> transactions = new ArrayList<>();
        
        for (TransactionRequest transaction : request) {
            Optional<Customer> customer = getCustomerById(transaction.getCustId());
         
            if (!customer.isPresent()) {
                log.error("[addTransactions] customer not found");
            }else{
                Transaction transactionEntity = new Transaction();
                transactionEntity.setAmount(transaction.getAmount());
                transactionEntity.setCustomer(customer.get());
                transactionEntity.setDate(transaction.getDate());
                transactionEntity.setPoints(RewardsUtil.calculatePoints(transaction.getAmount()));
                transactionEntity.setName(transaction.getName());
                transactions.add(transactionEntity);

            }

        }
        transactions = (List<Transaction>) transactionRepository.saveAll(transactions);
        log.info("[addTransactions] end");
        return transactions;
    }

    private Optional<Customer> getCustomerById(Long id){
        log.info("[getCustomerById] id: {}", id);
        return customerRepository.findById(id);
    }


    public Customer createCustomer(CustomerRequest request){
        log.info("[createCustomer]");
        Customer customer = new Customer();
        customer.setId(request.getId());
        customer.setName(request.getName());
        return customerRepository.save(customer);
    }

    public RewardsResponse getRewards(Map<String, String> request){
        log.info("[getRewards] start");
        YearMonth thisYearMonth = YearMonth.from(LocalDate.now());
        List<YearMonth> lastThreeMonths = Arrays.asList(thisYearMonth.minusMonths(2), 
                thisYearMonth.minusMonths(1), 
                    thisYearMonth);

        RewardsResponse response = new RewardsResponse();
        List<Customer> customers = customerRepository.findAll();

        if(request.get("month") != null 
            && !request.get("month").isEmpty()
                && request.get("month").equalsIgnoreCase("true")){
            log.debug("month true");
            response = getRewardsPerMonth(customers, lastThreeMonths);
        } else{
            log.debug("month false");
            response = getRewardsLastTrheeMonths(customers, lastThreeMonths);

        }
        log.info("[getRewards] end");
        return response;

    }

    private RewardsResponse getRewardsPerMonth(List<Customer> customers, List<YearMonth> lastThreeMonths){
        RewardsResponse response = new RewardsResponse();
        log.info("[getRewardsPerMonth] start");
        for (Customer customer : customers) {
            for (YearMonth month : lastThreeMonths) {
                Reward reward = new Reward();
                reward.setCustId(customer.getId());
                reward.setCustName(customer.getName());
                reward.setMonth(month.getMonth().toString());
                reward.setTotalPoints(getTotalPoints(customer.getTransactions(), month));
                reward.setTransactions(getTotalTransactions(customer.getTransactions(), month));
                response.getRewards().add(reward);
            }
        }
        log.info("[getRewardsPerMonth] end");
        return response;
    }

    Long getTotalPoints(List<Transaction> transactions, YearMonth month){
        log.info("[getTotalPoints] end");
        return transactions.stream()
            .filter(t -> month.compareTo(RewardsUtil.stringToYearMonth(t.getDate()))==0)
                .mapToLong(t -> t.getPoints()).sum();

    }

    int getTotalTransactions(List<Transaction> transactions, YearMonth month){
        log.info("[getTotalTransactions]");
        return transactions.stream()
            .filter(t -> month.compareTo(RewardsUtil.stringToYearMonth(t.getDate()))==0)
                .mapToInt(t -> 1).sum();
    }


    private Long getTotalPointsThreeMonths(List<Transaction> transactions){
        log.info("[getTotalPointsThreeMonths]");
        return transactions.stream().filter(t -> RewardsUtil.isWithinLastThreeMonths(t.getDate()))
            .mapToLong(t -> t.getPoints()).sum();
    }

    private RewardsResponse getRewardsLastTrheeMonths(List<Customer> customers, List<YearMonth> lastThreeMonths){
        log.info("[getRewardsLastTrheeMonths] start");
        RewardsResponse response = new RewardsResponse();
            for (Customer customer : customers) {
                Reward reward = new Reward();
                reward.setCustId(customer.getId());
                reward.setCustName(customer.getName());
                reward.setMonth(null);
                reward.setTotalPoints(getTotalPointsThreeMonths(customer.getTransactions()));
                reward.setTransactions(getTotalTransactionsThreeMonths(customer.getTransactions()));
                response.getRewards().add(reward);
            }
        log.info("[getRewardsLastTrheeMonths] end");
        return response;
    }

    private int getTotalTransactionsThreeMonths(List<Transaction> transactions){
        log.info("[getTotalTransactionsThreeMonths]");
        return transactions.stream().filter(t -> RewardsUtil.isWithinLastThreeMonths(t.getDate()))
            .mapToInt(t -> 1).sum();
    }


    public Customer deleteCustomer(String id){
        log.info("[deleteCustomer] id: {}", id);
        Optional<Customer> customer = customerRepository.findById(Long.parseLong(id));
        if(customer.isPresent()){
            customerRepository.deleteById(Long.parseLong(id));
            log.debug("[deleteCustomer] customer deleted, id: {}", id);
            return customer.get();
        }else{
            log.error("[deleteCustomer] customer not found, id: {}", id);
            throw new BusinessException(ErrorEnumUtil.CUSTOMER_NOT_FOUND, id, HttpStatus.NOT_FOUND);
        }
    }
    
    public String deleteAllCustomers(){
        log.info("[deleteAllCustomers]");
        try {
            customerRepository.deleteAll();
            log.debug("[deleteAllCustomers] customers were deleted");
        }catch(Exception e){
            log.error("[deleteAllCustomers] customer were not found");
            throw new BusinessException(ErrorEnumUtil.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return "Customers were deleted";
    }

    public Customer updateCustomer(String id, String name){
        log.info("[updateCustomer] id: {}", id);
        Optional<Customer> customer = customerRepository.findById(Long.parseLong(id));
        if(customer.isPresent()){
            Customer customerEntity = customer.get();
            customerEntity.setName(name);
            customerRepository.save(customerEntity);
            log.debug("[updateCustomer] customer updated, id: {}", id);
            return customerEntity;
        }else{
            log.error("[updateCustomer] customer not found, id: {}", id);
            throw new BusinessException(ErrorEnumUtil.CUSTOMER_NOT_FOUND, id, HttpStatus.NOT_FOUND);
        }
    }

    public Transaction deleteTransaction(String id){
        log.error("[deleteTransaction] id: {}", id);
        Optional<Transaction> transaction = transactionRepository.findById(Long.parseLong(id));
        if(transaction.isPresent()){
            transactionRepository.deleteById(Long.parseLong(id));
            log.debug("[deleteTransaction] transaction deleted, id: {}", id);
            return transaction.get();
        }else{
            log.error("[deleteTransaction] transaction not found, id: {}", id);
            throw new BusinessException(ErrorEnumUtil.TRANSACTION_NOT_FOUND, id, HttpStatus.NOT_FOUND);
        }
    }
    
    public String deleteAllTransactions(){
        log.info("[deleteAllTransactions]");
        try {
            transactionRepository.deleteAll();
            log.debug("[deleteAllTransactions] transactions were deleted");
        }catch(Exception e){
            log.error("[deleteAllTransactions] transactions were not found");
            throw new BusinessException(ErrorEnumUtil.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return "Transactions were deleted";
    }

    public Transaction updateTransaction(String id, String name, String date, String amount){
        log.info("[updateTransaction] id: {}", id);
        Optional<Transaction> transaction = transactionRepository.findById(Long.parseLong(id));
        if(transaction.isPresent()){
            Transaction transactionEntity = transaction.get();
            transactionEntity.setName(name);
            transactionEntity.setDate(date);
            transactionEntity.setAmount(Long.parseLong(amount));
            transactionRepository.save(transactionEntity);
            log.debug("[updateTransaction] transaction updated, id: {}", id);
            return transactionEntity;
        }else{
            log.error("[updateTransaction] transaction not found, id: {}", id);
            throw new BusinessException(ErrorEnumUtil.TRANSACTION_NOT_FOUND, id, HttpStatus.NOT_FOUND);
        }
    }

}