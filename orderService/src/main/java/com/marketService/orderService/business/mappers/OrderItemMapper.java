package com.marketService.orderService.business.mappers;

import com.marketService.orderService.business.repository.model.OrderItemDAO;
import com.marketService.orderService.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "orderId", target = "orderDAO.id")
    OrderItemDAO orderItemToDAO(OrderItem orderItem);

    @Mapping(source = "orderDAO.id", target = "orderId")
    OrderItem daoToOrderItem(OrderItemDAO orderItemDAO);
}
