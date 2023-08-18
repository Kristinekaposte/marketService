package com.marketService.orderService.business.service.impl;

import com.marketService.orderService.business.mappers.OrderItemMapper;
import com.marketService.orderService.business.mappers.OrderMapper;
import com.marketService.orderService.business.repository.OrderRepository;
import com.marketService.orderService.business.repository.model.OrderDAO;
import com.marketService.orderService.business.repository.model.OrderItemDAO;
import com.marketService.orderService.client.Client;
import com.marketService.orderService.model.Order;
import com.marketService.orderService.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private Client client;
    @InjectMocks
    private OrderServiceImpl orderService;




    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private List<OrderItemDAO> orderItemDAOList;
    private List<OrderDAO> orderDAOList;





    private OrderItem orderItem;
    private List<OrderItem> orderItemList;
    private Order order;


    @BeforeEach
    public void init() {
        orderDAO = createOrderDAO(orderItemDAOList);
        orderItemDAO = createOrderItemDAO(orderDAO);
        orderItemDAOList = createOrderItemDAOList(orderItemDAO);
        // orderDAO = createOrderDAO(orderItemDAOList);
        orderDAOList = createOrderDAOList(orderDAO);

        //____________________________________________
        orderItem = createOrderItem();
        orderItemList = createOrderItemList(orderItem);
        order = createOrder(orderItemList);

    }

    private OrderDAO createOrderDAO(List<OrderItemDAO> orderItemDAOList) {
        return new OrderDAO(1L, "ORD-12345678", 1L, LocalDateTime.now(), 20.00, orderItemDAOList);
    }

//    private OrderDAO createOrderDAO() {
//        OrderDAO orderDAO = new OrderDAO();
//        orderDAO.setId(1L);
//        orderDAO.setOrderNumber("ORD-12345678");
//        orderDAO.setCustomerId(1L);
//        orderDAO.setOrderTime(LocalDateTime.now());
//        orderDAO.setTotalPrice(20.00);
//
//        ArrayList<OrderItemDAO> list = new ArrayList<>();
//        list.add(new OrderItemDAO(1L,orderDAO,2L,10.0, 2));
//
//        orderDAO.setOrderItemDAOList(list);
//
//        return orderDAO;
//    }

    private OrderItemDAO createOrderItemDAO(OrderDAO orderDAO) {
        return new OrderItemDAO(1L, orderDAO, 2L, 10.00, 2);
    }
    private List<OrderItemDAO> createOrderItemDAOList(OrderItemDAO orderItemDAO) {
        List<OrderItemDAO> list = new ArrayList<>();
        list.add(orderItemDAO);
        return list;
    }

    private List<OrderDAO> createOrderDAOList(OrderDAO orderDAO) {
        List<OrderDAO> list = new ArrayList<>();
        list.add(orderDAO);
        list.add(orderDAO);
        return list;
    }
    // ORDER

    private Order createOrder(List<OrderItem> orderItemList) {
        return new Order(1L, "ORD-12345678", 1L, LocalDateTime.now(), 20.00, orderItemList);
    }

    private OrderItem createOrderItem() {
        return new OrderItem(1L, 1L, 2L, 10.00, 2);
    }

    private List<OrderItem> createOrderItemList(OrderItem orderItem) {
        List<OrderItem> list = new ArrayList<>();
        list.add(orderItem);
        return list;
    }

    @Test
    @DisplayName("Should not place the order when the customer does not exist")
    void placeOrderWhenCustomerDoesNotExist() {        // Mocking the client response for checking customer existence
        ResponseEntity<Object> customerResponse = ResponseEntity.notFound().build();
        when(client.checkCustomerExistence(anyLong())).thenReturn(customerResponse);
        Order result = orderService.placeOrder(order);
        assertNull(result);
        verify(client, times(1)).checkCustomerExistence(anyLong());
        verifyNoMoreInteractions(client);
        verifyNoInteractions(orderRepository, orderMapper, orderItemMapper);
    }

//    @Test
//    @DisplayName("Should place the order when the customer exists and all product IDs are valid")
//    void placeOrderWhenCustomerExistsAndProductIdsAreValid() {        // Mocking the client response for checking customer existence
//        ResponseEntity<Object> customerResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(client.checkCustomerExistence(anyLong())).thenReturn(customerResponse);
//
//        // Mocking the client response for getting product information
//        Map<Long, Double> productInfo = new HashMap<>();
//        productInfo.put(2L, 10.00);
//        ResponseEntity<Map<Long, Double>> productInfoResponse = new ResponseEntity<>(productInfo, HttpStatus.OK);
//        when(client.getProductInfo(anyList())).thenReturn(productInfoResponse);
//
//        when(orderRepository.save(orderDAO)).thenReturn(orderDAO);
//        when(orderMapper.daoToOrder(orderDAO)).thenReturn(order);
//
//
//        // Calling the placeOrder method
//        Order result = orderService.placeOrder(order);
//
//        // Verifying the interactions
//        verify(client, times(1)).checkCustomerExistence(anyLong());
//        verify(client, times(1)).getProductInfo(anyList());
//
//        assertEquals(order, result);
//    }


//    @Test
//    @DisplayName("Should not place the order when some product IDs are invalid")
//    void placeOrderWhenSomeProductIdsAreInvalid() {        // Mocking the client response for checking customer existence
//        ResponseEntity<Object> customerResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(client.checkCustomerExistence(anyLong())).thenReturn(customerResponse);
//
//        // Mocking the client response for getting product information
//        Map<Long, Double> productInfo = new HashMap<>();
//        productInfo.put(2L, 10.00);
//        ResponseEntity<Map<Long, Double>> productInfoResponse = new ResponseEntity<>(productInfo, HttpStatus.OK);
//        when(client.getProductInfo(anyList())).thenReturn(productInfoResponse);
//
//        // Mocking the order repository save method
//        OrderDAO savedOrderDAO = new OrderDAO(1L, "ORD-12345678", 1L, LocalDateTime.now(), 20.00, orderItemDAOList);
//        when(orderRepository.save(any(OrderDAO.class))).thenReturn(savedOrderDAO);
//
//        // Mocking the order mapper
//        Order savedOrder = new Order(1L, "ORD-12345678", 1L, LocalDateTime.now(), 20.00, orderItemList);
//        when(orderMapper.daoToOrder(any(OrderDAO.class))).thenReturn(savedOrder);
//
//        // Placing the order
//        Order result = orderService.placeOrder(order);
//
//        // Verifying the client method calls
//        verify(client, times(1)).checkCustomerExistence(anyLong());
//        verify(client, times(1)).getProductInfo(anyList());
//
//        // Verifying the order repository method calls
//        verify(orderRepository, times(1)).save(any(OrderDAO.class));
//
//        // Verifying the order mapper method calls
//        verify(orderMapper, times(1)).daoToOrder(any(OrderDAO.class));
//
//        // Asserting the result
//        assertNotNull(result);
//        assertEquals(savedOrder, result);
//    }
//
//    @Test
//    @DisplayName("Should autogenerate order number and time when placing an order")
//    void autogenerateOrderNumberAndTimeWhenPlacingOrder() {        // Mocking the client response for checking customer existence
//        ResponseEntity<Object> customerResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(client.checkCustomerExistence(anyLong())).thenReturn(customerResponse);
//
//        // Mocking the client response for getting product information
//        Map<Long, Double> productInfo = new HashMap<>();
//        productInfo.put(2L, 10.00);
//        ResponseEntity<Map<Long, Double>> productInfoResponse = new ResponseEntity<>(productInfo, HttpStatus.OK);
//        when(client.getProductInfo(anyList())).thenReturn(productInfoResponse);
//
//        // Mocking the order repository save method
//        OrderDAO savedOrderDAO = new OrderDAO(1L, "ORD-12345678", 1L, LocalDateTime.now(), 20.00, orderItemDAOList);
//        when(orderRepository.save(any(OrderDAO.class))).thenReturn(savedOrderDAO);
//
//        // Mocking the order mapper
//        Order savedOrder = new Order(1L, "ORD-12345678", 1L, LocalDateTime.now(), 20.00, orderItemList);
//        when(orderMapper.daoToOrder(any(OrderDAO.class))).thenReturn(savedOrder);
//
//        // Placing the order
//        Order result = orderService.placeOrder(order);
//
//        // Verifying the autogeneration of order number and time
//        assertNotNull(result.getOrderNumber());
//        assertNotNull(result.getOrderTime());
//        assertNotEquals("", result.getOrderNumber());
//
//        // Verifying the client method calls
//        verify(client, times(1)).checkCustomerExistence(anyLong());
//        verify(client, times(1)).getProductInfo(anyList());
//
//        // Verifying the order repository method calls
//        verify(orderRepository, times(1)).save(any(OrderDAO.class));
//
//        // Verifying the order mapper method calls
//        verify(orderMapper, times(1)).daoToOrder(any(OrderDAO.class));
//    }
//

//    @Test
//    @DisplayName("Should calculate total price correctly when placing an order")
//    void calculateTotalPriceWhenPlacingOrder() {        // Mocking the client response for checking customer existence
//        ResponseEntity<Object> customerResponse = ResponseEntity.ok().build();
//        when(client.checkCustomerExistence(anyLong())).thenReturn(customerResponse);
//
//        // Mocking the client response for getting product information
//        Map<Long, Double> productInfo = new HashMap<>();
//        productInfo.put(2L, 10.00);
//        ResponseEntity<Map<Long, Double>> productInfoResponse = ResponseEntity.ok(productInfo);
//        when(client.getProductInfo(anyList())).thenReturn(productInfoResponse);
//
//        // Mocking the order repository save method
//        OrderDAO savedOrderDAO = new OrderDAO(1L, "ORD-12345678", 1L, LocalDateTime.now(), 20.00, orderItemDAOList);
//        when(orderRepository.save(any(OrderDAO.class))).thenReturn(savedOrderDAO);
//
//        // Mocking the order mapper
//        Order savedOrder = new Order(1L, "ORD-12345678", 1L, LocalDateTime.now(), 20.00, orderItemList);
//        when(orderMapper.daoToOrder(any(OrderDAO.class))).thenReturn(savedOrder);
//
//        // Placing the order
//        Order result = orderService.placeOrder(order);
//
//        // Verifying the interactions
//        verify(client, times(1)).checkCustomerExistence(anyLong());
//        verify(client, times(1)).getProductInfo(anyList());
//        verify(orderRepository, times(1)).save(any(OrderDAO.class));
//        verify(orderMapper, times(1)).daoToOrder(any(OrderDAO.class));
//
//        // Asserting the result
//        assertNotNull(result);
//        assertEquals(savedOrder, result);
//    }


    @Test
    void testGetAllOrderEntries_Successful() {
        when(orderRepository.findAll()).thenReturn(orderDAOList);
        when(orderMapper.daoToOrder(orderDAO)).thenReturn(order);
        List<Order> list = orderService.getAllOrders();
        assertEquals(2, list.size());
        assertEquals(order.getId(), list.get(0).getId());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllOrders_ListEmpty_Successful() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        List<Order> result = orderService.getAllOrders();
        verify(orderRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindOrderByOrderNumber_Successful() {
        when(orderRepository.findByOrderNumber(anyString())).thenReturn(Optional.of(orderDAO));
        when(orderMapper.daoToOrder(orderDAO)).thenReturn(order);
        Optional<Order> actualResult = orderService.findOrderByOrderNumber("ORD-12345678");
        assertTrue(actualResult.isPresent());
        assertEquals(order, actualResult.get());
        verify(orderRepository, times(1)).findByOrderNumber("ORD-12345678");
        verify(orderMapper, times(1)).daoToOrder(orderDAO);
    }

    @Test
    void testFindOrderByOrderNumber_NonExistingOrderNumber_Failed() {
        when(orderRepository.findByOrderNumber(anyString())).thenReturn(Optional.empty());
        Optional<Order> result = orderService.findOrderByOrderNumber("ORD-number12");
        assertFalse(result.isPresent());
        verify(orderRepository, times(1)).findByOrderNumber(anyString());
    }
}