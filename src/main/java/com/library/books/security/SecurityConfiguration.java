package com.library.books.security;

import com.library.books.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home", "/register", "/login", "/emailExists",
                                "/authenticate", "/static/**", "/access_denied").permitAll()
                        .requestMatchers("/**").authenticated()
                )
                .apply(jwtConfigurer())
                .and()
                .csrf().disable(); // Disable CSRF protection off, using JWT

        return http.build();
    }

    private JwtConfigurer jwtConfigurer() {
        return new JwtConfigurer(jwtAuthenticationFilter());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        // Use the autowired customUserDetailsService and jwtTokenUtil beans
        return new JwtAuthenticationFilter(customUserDetailsService, jwtTokenUtil);
    }
}
