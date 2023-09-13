package com.marketService.orderService.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

//@Configuration
@Component
@Slf4j
public class Client {

    @Autowired
    private WebClient webClient; //  not nessesary after products serive will be set with security

//        @Autowired
//    private WebClient.Builder webClientBuilder;


    private WebClient.Builder webClientBuilder;

    @Autowired
    public Client(@Lazy WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter((request, next) -> {
                    if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken) {
                        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                        String token = jwtToken.getToken().getTokenValue();
                        log.info("JWT Token Value: {}", token);
                        // Create a new ClientRequest with the "Authorization" header.
                        ClientRequest bearerRequest = ClientRequest.from(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .build();
                        log.info("bearerRequest: {}", bearerRequest);
                        return next.exchange(bearerRequest);
                    }
                    return next.exchange(request);
                });
    }
    /**
     * Checks if a customer with the given ID exists from CustomerService endpoint.
     *
     * @param customerId The customer id to check.
     * @return ResponseEntity containing info about if customer was found by id.
     */

    public ResponseEntity<Object> checkCustomerExistence(Long customerId) {
        WebClient webClient = webClientBuilder().build();
        log.info("Checking customer existence for customerId: {}", customerId);
        return webClient.get()
                .uri("http://127.0.0.1:5050/api/v1/customer/getById/{id}", customerId)
//                .attributes(
//                        ServerOAuth2AuthorizedClientExchangeFilterFunction
//                                .clientRegistrationId("myOAuthServer"))
                .retrieve()
                .toEntity(Object.class)
                .block();
    }


    /**
     * Retrieves products information from the products service,by sending an asynchronous HTTP GET request using WebClient.
     * Response is retrieved and converted to a Map<Long, Double>
     *
     * @param productIds The list of productIds to check.
     * @return A ResponseEntity containing the list of existing productIds and prices.
     */
    public ResponseEntity<Map<Long, Double>> getProductInfo(List<Long> productIds) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:5051/api/v1/products/getProductInfo")
                .queryParam("productIds", productIds);

        return
                webClient.get()
                .uri(uriBuilder.toUriString())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<Long, Double>>() {
                })
                .block();
    }
}
//public ResponseEntity<Object> checkCustomerExistence(Long customerId) {
//    log.info("Checking customer existence for customerId: {}", customerId);
//    return webClient.get()
//            .uri("http://localhost:5050/api/v1/customer/getById/"+ customerId)
//            .retrieve()
//            .toEntity(Object.class)
//            .block();
//}