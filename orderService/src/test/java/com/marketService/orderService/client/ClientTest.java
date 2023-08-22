package com.marketService.orderService.client;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;


@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @InjectMocks
    private Client client;

    @Mock
    private WebClient webClient;

    @Mock
    private RequestHeadersUriSpec<?> uriSpec;

    @Mock
    private RequestHeadersSpec<?> headersSpec;

    @Mock
    private ResponseSpec responseSpec;

}
