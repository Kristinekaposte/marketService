package com.marketService.productsService.business.repository;

import com.marketService.productsService.business.repository.model.CategoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDAO,Long> {

    boolean existsByName(String name);
}
