package com.marketService.customerService.business.service.impl;

import com.marketService.customerService.business.mappers.AddressMapper;
import com.marketService.customerService.business.mappers.CustomerMapper;
import com.marketService.customerService.business.repository.CustomerRepository;
import com.marketService.customerService.business.repository.model.AddressDAO;
import com.marketService.customerService.business.repository.model.CustomerDAO;
import com.marketService.customerService.business.service.CustomerService;
import com.marketService.customerService.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AddressMapper addressMapper;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> list = customerRepository.findAll()
                .stream()
                .map(customerMapper::daoToCustomer)
                .collect(Collectors.toList());
        log.info("Size of the Customer list: {}", list.size());
        return list;
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        Optional<CustomerDAO> customerDAO = customerRepository.findById(id);
        if (!customerDAO.isPresent()) {
            log.info("Customer with id {} does not exist.", id);
            return Optional.empty();
        }
        log.info("Customer with id {} found.", id);
        return customerDAO.map(customerMapper::daoToCustomer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        Optional<CustomerDAO> customerDAO = customerRepository.findByEmail(email);
        if (!customerDAO.isPresent()) {
            log.info("Customer with email {} does not exist.", email);
            return Optional.empty();
        }
        log.info("Customer with email {} found.", email);
        return customerDAO.map(customerMapper::daoToCustomer);
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        CustomerDAO newCustomerDAO = customerMapper.customerToDAO(customer);
        newCustomerDAO = customerRepository.save(newCustomerDAO);
        if (newCustomerDAO != null) {
            log.info("Customer with ID {} is saved successfully.", newCustomerDAO.getId());
            return customerMapper.daoToCustomer(newCustomerDAO);
        }
        log.warn("Failed to save customer.");
        return null;
    }

    /**
     * Method is running within a transaction.It ensures that the method is executed as a single unit,
     * and if any part of the method fails, the entire transaction is rolled back.
     *
     * Update is happening only in URL providing id,
     * addressDAO id in customerDAO table is ignored at request and will not be changed  because of OneToOne rel.
     * id - in customerDAO and addressDAO table are ignored at request to avoid mismatched id's is request body
     */
    @Override
    @Transactional
    public Customer editCustomerById(Long id, Customer updatedCustomer) {
        CustomerDAO existingCustomerDAO = customerRepository.findById(id).orElse(null);
        if (existingCustomerDAO != null) {
            BeanUtils.copyProperties(updatedCustomer, existingCustomerDAO, "id", "addressDAO");
            AddressDAO existingAddressDAO = existingCustomerDAO.getAddressDAO();
            AddressDAO updatedAddressDAO = addressMapper.addressToDAO(updatedCustomer.getAddress());
            BeanUtils.copyProperties(updatedAddressDAO, existingAddressDAO, "id");
            existingCustomerDAO.setPassword(passwordEncoder.encode(updatedCustomer.getPassword()));
            Customer updatedCustomerObject = customerMapper.daoToCustomer(customerRepository.save(existingCustomerDAO));
            log.info("Updated customer details: {}", updatedCustomerObject);
            return updatedCustomerObject;
        }
        log.warn("Customer with ID {} not found for updating.", id);
        return null;
    }




    @Transactional
    @Override
    public Boolean deleteCustomerById(Long id) {
        if (isCustomerPresent(id)) {
            customerRepository.deleteById(id);
            log.info("Customer entry with id: {} is deleted", id);
            return true;
        } else
            log.warn("Customer entry with id: {} does not exist, could not delete", id);
        return false;
    }

    @Override
    public boolean isEmailExisting(String email) {
        boolean emailExists = customerRepository.existsByEmail(email);
        log.info("Email '{}' exists in database: {}", email, emailExists);
        return emailExists;
    }

    @Override
    public boolean isCustomerPresent(Long id) {
        return customerRepository.existsById(id);
    }

}
