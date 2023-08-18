package com.marketService.orderService.business.repository;

import com.marketService.orderService.business.repository.model.OrderItemDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemDAO,Long> {
}
