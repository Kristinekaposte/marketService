package com.marketService.customerService.business.mappers;

import com.marketService.customerService.business.repository.model.CustomerDAO;
import com.marketService.customerService.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {

    @Mapping(source = "address", target = "addressDAO")
    CustomerDAO customerToDAO(Customer customer);

    @Mapping(source = "addressDAO", target = "address")
    Customer daoToCustomer(CustomerDAO customerDAO);
}
