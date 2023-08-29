package com.marketService.customerService.security.impl;

import com.marketService.customerService.business.service.CustomerService;
import com.marketService.customerService.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Attempting to load user by email: {}", email);
        Optional<Customer> customerOptional = customerService.findByEmail(email);
        if (!customerOptional.isPresent()) {
            log.warn("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        log.info("User found with email: {}", email);
        // Using simple authority because you don't have any roles,
        // so "ROLE_USER" will be authority for all users
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        Customer customer = customerOptional.get();
        log.info("Creating UserDetails for user: {}", email);
        return new User(
                customer.getEmail(), customer.getPassword(), authorities
        );
    }

}
