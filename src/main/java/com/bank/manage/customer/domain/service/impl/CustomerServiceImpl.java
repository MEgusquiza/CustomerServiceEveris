package com.bank.manage.customer.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bank.manage.customer.domain.repository.CustomerRepository;
import com.bank.manage.customer.domain.service.CustomerService;
import com.bank.manage.customer.persistence.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	   @Autowired
	   private CustomerRepository customerRepository;
	   
	    
		public Mono<Customer> getByIdCustomer(String id) {
			return customerRepository.findById(id)
					.switchIfEmpty(Mono.error(new Exception("No Customer found with Id: " + id)));
		}
	
	    public Flux<Customer> getAllCustomer() {
	        long start = System.currentTimeMillis();
	        Flux<Customer> customers = customerRepository.findAll() ;
	        long end = System.currentTimeMillis();
	        LOGGER.debug("customerRepositoryTotal execution time : " + (end - start));
	        return customers;
	    }
	  
	    public Mono<Customer> createCustomer(Customer customer) {
	        return customerRepository.insert(customer);
	    }   
	
		public Mono<Customer> updateCustomer(Customer customer , String id) {
			return getByIdCustomer(id).doOnNext(
	                  findCustomer -> {
	                	  findCustomer.setFirstName(customer.getFirstName());
	                	  findCustomer.setLastName(customer.getLastName());
	                	  findCustomer.setDocumentType(customer.getDocumentType());
	                	  findCustomer.setMail(customer.getMail());
	                	  findCustomer.setPhone(customer.getPhone());
	                	  findCustomer.setState(customer.getState());
	                	  customerRepository.save(findCustomer).subscribe();
	                	  }).switchIfEmpty(Mono.error(new Exception("No Customer found.... " + id)));
		}
	
	    public Mono<Void> deleteByIdCustomer(String id){
	        return customerRepository.deleteById(id);
	    }
 
}
