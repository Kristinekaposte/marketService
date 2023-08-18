package com.marketService.customerService.business.mappers;

import com.marketService.customerService.business.repository.model.AddressDAO;
import com.marketService.customerService.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDAO addressToDAO (Address address);
    Address daoToAddress (AddressDAO addressDAO);
}
