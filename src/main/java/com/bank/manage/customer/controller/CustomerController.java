package com.bank.manage.customer.controller;


import com.bank.manage.customer.domain.service.impl.CustomerServiceImpl;
import com.bank.manage.customer.persistence.entity.Customer;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/customer")
public class CustomerController {
   
	private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	
	
    @Autowired
	private CustomerServiceImpl  customerServiceImpl;
	
     //End point by get customer  (GET)
	@GetMapping("/{id}")  
	public ResponseEntity<Mono<Customer>> getCustomer(@PathVariable("id") String id) {	
		return ResponseEntity.ok(customerServiceImpl.getByIdCustomer(id));
	}
	
	 //End point by get all customers (GET)
    @GetMapping //(value="/",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getAllCustomers() {
        return customerServiceImpl.getAllCustomer();
    }
    
    //End point by save customers  (POST)
	@PostMapping("/save")
	public ResponseEntity<Mono<Customer>> createCustomer(@RequestBody Customer customer) {
		return ResponseEntity.ok(customerServiceImpl.createCustomer(customer) );
	}
	
	 //End point by update customers (PUT)
    @PutMapping("/update/{id}")
    public Mono<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable String id) {
    	LOGGER.debug("Updating User with user-id = {}.", id);
        return customerServiceImpl.updateCustomer(customer, id);
    }
    
    //End point by delete customers (DELETE)
	 @PostMapping("/delete/{id}")
    public Mono<Void> deleteCustomer(@PathVariable("id") String id){
        return customerServiceImpl.deleteByIdCustomer(id);
    }
    
}
