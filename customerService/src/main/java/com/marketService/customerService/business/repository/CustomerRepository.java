package com.marketService.customerService.business.repository;

import com.marketService.customerService.business.repository.model.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO,Long> {

    boolean existsByEmail(String email);
    Optional<CustomerDAO> findByEmail (String email);

}
