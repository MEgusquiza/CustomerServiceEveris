package com.bank.manage.customer.domain.service;

import com.bank.manage.customer.persistence.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerService {

    public Mono<Customer> getByIdCustomer(String id) ;
    public Flux<Customer> getAllCustomer() ;
    public Mono<Customer> createCustomer(Customer customer) ;
    public Mono<Customer> updateCustomer(Customer customer , String id);
    public Mono<Void> deleteByIdCustomer(String id);
    
}
