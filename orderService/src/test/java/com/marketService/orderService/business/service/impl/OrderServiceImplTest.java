//package com.marketService.orderService.business.service.impl;
//
//import com.marketService.orderService.business.mappers.OrderMapper;
//import com.marketService.orderService.business.repository.OrderRepository;
//import com.marketService.orderService.business.repository.model.OrderDAO;
//import com.marketService.orderService.business.repository.model.OrderItemDAO;
//import com.marketService.orderService.client.Client;
//import com.marketService.orderService.model.Order;
//import com.marketService.orderService.model.OrderItem;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//public class OrderServiceImplTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//    @Mock
//    private OrderMapper orderMapper;
//    @Mock
//    private Client client;
//    @InjectMocks
//    private OrderServiceImpl orderService;
//
//    private OrderDAO orderDAO;
//    private List<OrderDAO> orderDAOList;
//    private Order order;
//
//    @BeforeEach
//    public void init() {
//        orderDAO = createOrderDAO();
//        orderDAOList = createOrderDAOList(orderDAO);
//        order = createOrder();
//    }
//
//    @Test
//    void testGetAllOrderEntries_Successful() {
//        when(orderRepository.findAll()).thenReturn(orderDAOList);
//        when(orderMapper.daoToOrder(orderDAO)).thenReturn(order);
//        List<Order> list = orderService.getAllOrders();
//        assertEquals(2, list.size());
//        assertEquals(order.getId(), list.get(0).getId());
//        verify(orderRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetAllOrders_ListEmpty_Successful() {
//        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
//        List<Order> result = orderService.getAllOrders();
//        verify(orderRepository, times(1)).findAll();
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testFindOrderByOrderNumber_Successful() {
//        when(orderRepository.findByOrderNumber(anyString())).thenReturn(Optional.of(orderDAO));
//        when(orderMapper.daoToOrder(orderDAO)).thenReturn(order);
//        Optional<Order> actualResult = orderService.findOrderByOrderNumber("ORD-12345678");
//        assertTrue(actualResult.isPresent());
//        assertEquals(order, actualResult.get());
//        verify(orderRepository, times(1)).findByOrderNumber("ORD-12345678");
//        verify(orderMapper, times(1)).daoToOrder(orderDAO);
//    }
//
//    @Test
//    void testFindOrderByOrderNumber_NonExistingOrderNumber_Failed() {
//        when(orderRepository.findByOrderNumber(anyString())).thenReturn(Optional.empty());
//        Optional<Order> result = orderService.findOrderByOrderNumber("ORD-number12");
//        assertFalse(result.isPresent());
//        verify(orderRepository, times(1)).findByOrderNumber(anyString());
//    }
//
//    @Test
//    void testPlaceOrder_WhenCustomerAndAllProductsExist_ThenOrderIsPlaced_Successful() {
//        Map<Long, Double> productInfo = new HashMap<>();
//        productInfo.put(1L, 5.0);
//        when(client.checkCustomerExistence(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        when(client.getProductInfo(anyList())).thenReturn(new ResponseEntity<>(productInfo, HttpStatus.OK));
//        when(orderMapper.orderToDAO(any(Order.class))).thenReturn(new OrderDAO());
//        when(orderMapper.daoToOrder(any(OrderDAO.class))).thenReturn(order);
//        when(orderRepository.save(any(OrderDAO.class))).thenReturn(new OrderDAO());
//        Order result = orderService.placeOrder(order);
//        assertEquals(order, result);
//        verify(orderRepository, times(1)).save(any(OrderDAO.class));
//    }
//
//    @Test
//    void testPlaceOrder_WhenCustomerDoesNotExist_Failed() {
//        Order order = new Order();
//        order.setCustomerId(1L);
//        when(client.checkCustomerExistence(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        Order result = orderService.placeOrder(order);
//        assertNull(result);
//        verify(client, times(1)).checkCustomerExistence(anyLong());
//    }
//
//    @Test
//    void testPlaceOrder_WhenSomeProductsNotExist_Failed() {
//        Order order = new Order();
//        order.setCustomerId(1L);
//        order.setOrderItems(Collections.singletonList(new OrderItem()));
//        Map<Long, Double> productInfo = new HashMap<>();
//        when(client.checkCustomerExistence(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        when(client.getProductInfo(anyList())).thenReturn(new ResponseEntity<>(productInfo, HttpStatus.OK));
//        Order result = orderService.placeOrder(order);
//        assertNull(result);
//    }
//
//    @Test
//    void testSetOrderDAOForOrderItemsWhenOrderDAOAndProductInfoProvided_Successful() {
//        Map<Long, Double> productInfo = new HashMap<>();
//        productInfo.put(1L, 5.0);
//        OrderItemDAO orderItemDAO = new OrderItemDAO(1L, orderDAO, 1L, 5.0, 1);
//        orderDAO.setOrderItemDAOList(Collections.singletonList(orderItemDAO));
//        orderService.setOrderDAOForOrderItems(orderDAO, productInfo);
//        assertEquals(orderDAO, orderDAO.getOrderItemDAOList().get(0).getOrderDAO());
//        assertEquals(productInfo.get(1L), orderDAO.getOrderItemDAOList().get(0).getItemPrice());
//    }
//
//    private OrderDAO createOrderDAO() {
//        return new OrderDAO(1L, "ORD-12345678", 1L, LocalDateTime.of(2000, 10, 20, 14, 44, 55), 20.00, new ArrayList<>());
//    }
//
//
//    private List<OrderDAO> createOrderDAOList(OrderDAO orderDAO) {
//        List<OrderDAO> list = new ArrayList<>();
//        list.add(orderDAO);
//        list.add(orderDAO);
//        return list;
//    }
//
//    private Order createOrder() {
//        return new Order(1L, "ORD-12345678", 1L, LocalDateTime.of(2000, 10, 20, 14, 44, 55),
//                5.00, Collections.singletonList(new OrderItem(1L, 1L, 1L, 5.00, 1)));
//    }
//}
