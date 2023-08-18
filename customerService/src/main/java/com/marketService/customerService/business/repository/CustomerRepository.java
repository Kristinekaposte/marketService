package com.marketService.customerService.business.repository;

import com.marketService.customerService.business.repository.model.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO,Long> {

    boolean existsByEmail(String email);

}
