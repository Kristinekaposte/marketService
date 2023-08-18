package com.marketService.productsService.business.mappers;

import com.marketService.productsService.business.repository.model.CategoryDAO;
import com.marketService.productsService.model.Category;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface CategoryMapper {

    CategoryDAO categoryToDAO (Category category);

    Category daoToCategory (CategoryDAO categoryDAO);

}
