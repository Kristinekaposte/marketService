//package com.marketService.customerService.security;
//
//import com.marketService.customerService.business.exceptions.CustomAccessDeniedHandler;
//import com.marketService.customerService.business.service.CustomerService;
//import com.marketService.customerService.security.impl.CustomUserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//    @Autowired
//    private CustomerService customerService;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers("/","/api/v1/customer/save" ,"/access-denied", "/error").permitAll() // Allows unauthenticated access
//                                .antMatchers("/api/v1/customer/**").hasAuthority("USER") // Require authority for other endpoints
//                                .antMatchers("/oauth2/**").permitAll()
//                                .anyRequest()
//                                .authenticated()
//                )
//                .formLogin(withDefaults())
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
//        http.csrf().disable().httpBasic().disable(); // Disable CSRF and HTTP Basic for JWT usage
//
//        return http.build();
//    }
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return new CustomAccessDeniedHandler();
//    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomUserDetailsServiceImpl(customerService);
//    }
//
//}
////    @Bean
////    public CustomUserDetailsServiceImpl customUserDetailsServiceImpl() {
////        return new CustomUserDetailsServiceImpl(customerService);
////    }
//
////
////    @Autowired
//////    public  void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder){
//////        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
//////    }
//
//
////    @Autowired
////    private CustomAuthenticationProviderImpl customAuthenticationProvider;
//
//
//
