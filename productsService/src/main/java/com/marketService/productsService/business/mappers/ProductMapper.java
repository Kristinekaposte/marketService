package com.marketService.productsService.business.mappers;

import com.marketService.productsService.business.repository.model.ProductDAO;
import com.marketService.productsService.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDAO productToDAO (Product product);
    Product daoToProduct (ProductDAO productDAO);
}
