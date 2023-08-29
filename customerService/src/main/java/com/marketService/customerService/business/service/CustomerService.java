package com.marketService.customerService.business.service;

import com.marketService.customerService.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findByEmail(String email);

    Customer saveCustomer(Customer customer);

    Customer editCustomerById(Long id, Customer updatedCustomer);

    Boolean deleteCustomerById(Long id);

    boolean isEmailExisting(String email);
    boolean isCustomerPresent(Long id);
}
