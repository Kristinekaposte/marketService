package com.marketService.orderService.business.exceptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomizedResponseEntityExceptionHandlerTest {

    @InjectMocks
    private CustomizedResponseEntityExceptionHandler exceptionHandler;

    @Test
    void testHandleNotFound_WhenExceptionThrown() {
        String exceptionMessage = "Resource not found";
        WebClientResponseException.NotFound exception = mock(WebClientResponseException.NotFound.class);
        when(exception.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleNotFound(exception);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody().getMessage());
    }
}