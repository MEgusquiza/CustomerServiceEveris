package com.bank.manage.customer.controller;


import com.bank.manage.customer.domain.service.impl.CustomerServiceImpl;
import com.bank.manage.customer.persistence.entity.Customer;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
  private final Logger Logger = LoggerFactory.getLogger(CustomerController.class);

	
    @Autowired
	private CustomerServiceImpl  customerServiceImpl;
	
    @Bulkhead(name = "customer-service", fallbackMethod = "greetingFallBack",type = Bulkhead.Type.THREADPOOL)
    @TimeLimiter(name = "customer-service")
    @GetMapping("/{id}")  
	public ResponseEntity<Mono<Customer>> getCustomer(@PathVariable("id") String id) {	
    	Logger.debug(" EndPoint get by customer {} ", id);
		return ResponseEntity.ok(customerServiceImpl.getByIdCustomer(id));
     }
	
    @GetMapping 
    public Flux<Customer> getAllCustomers() {
    	Logger.info(" EndPoint get by all customers {} ");
        return customerServiceImpl.getAllCustomer();
    }
    
	@PostMapping("/save")
	public ResponseEntity<Mono<Customer>> createCustomer(@RequestBody Customer customer) {
		Logger.info(" EndPoit get by all customers {} ");
		return ResponseEntity.ok(customerServiceImpl.createCustomer(customer) );
	}
	
    @PutMapping("/update/{id}")
    public Mono<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable String id) {
    	Logger.debug("Updating User with user-id = {}.", id);
        return customerServiceImpl.updateCustomer(customer, id);
    }
    
	 @PostMapping("/delete/{id}")
    public Mono<Void> deleteCustomer(@PathVariable("id") String id){
		 Logger.debug("End point by delete customers ", id);
        return customerServiceImpl.deleteByIdCustomer(id);
    }
    
	 public String greetingFallBack(Throwable t) {
		    return "Fall back, timeout";
		}
}
