package com.marketService.orderService.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @InjectMocks
    private Client client;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> uriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> headersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Test
    void testCheckCustomerExistence_customerFound() {
        doReturn(uriSpec).when(webClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString(), anyLong());
        doReturn(responseSpec).when(headersSpec).retrieve();
        ResponseEntity<Object> foundResponseEntity = ResponseEntity.ok().build();
        doReturn(Mono.just(foundResponseEntity)).when(responseSpec).toEntity(Object.class);
        Long customerId = 1L;
        ResponseEntity<Object> response = client.checkCustomerExistence(customerId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void testCheckCustomerExistence_customerNotFound() {
        doReturn(uriSpec).when(webClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString(), anyLong());
        doReturn(responseSpec).when(headersSpec).retrieve();
        ResponseEntity<Object> notFoundResponseEntity = ResponseEntity.notFound().build();
        doReturn(Mono.just(notFoundResponseEntity)).when(responseSpec).toEntity(Object.class);
        Long customerId = 123L;
        ResponseEntity<Object> response = client.checkCustomerExistence(customerId);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetProductInfo_success() {
        doReturn(uriSpec).when(webClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString());
        doReturn(responseSpec).when(headersSpec).retrieve();
        Map<Long, Double> mockResponse = new HashMap<>();
        mockResponse.put(1L, 10.0);
        mockResponse.put(2L, 20.0);
        ResponseEntity<Map<Long, Double>> responseEntity = ResponseEntity.ok(mockResponse);
        doReturn(Mono.just(responseEntity)).when(responseSpec).toEntity(ArgumentMatchers.<ParameterizedTypeReference<Map<Long, Double>>>any());
        List<Long> productIds = Arrays.asList(1L, 2L);
        ResponseEntity<Map<Long, Double>> response = client.getProductInfo(productIds);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void testGetProductInfo_noProducts() {
        doReturn(uriSpec).when(webClient).get();
        doReturn(headersSpec).when(uriSpec).uri(anyString());
        doReturn(responseSpec).when(headersSpec).retrieve();
        ResponseEntity<Map<Long, Double>> responseEntity = ResponseEntity.ok(Collections.emptyMap());
        doReturn(Mono.just(responseEntity)).when(responseSpec).toEntity(ArgumentMatchers.<ParameterizedTypeReference<Map<Long, Double>>>any());
        List<Long> emptyProductIds = Collections.emptyList();
        ResponseEntity<Map<Long, Double>> response = client.getProductInfo(emptyProductIds);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyMap(), response.getBody());
    }
}
