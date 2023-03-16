package com.library.books.security;

import com.library.books.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfiguration {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home", "/register", "/login", "/authenticate").permitAll()
                        .requestMatchers("/**").permitAll()
//                        .anyRequest().denyAll() #Validate
                )
                .apply(jwtConfigurer())
                .and()
                .csrf().disable(); // Disable CSRF protection, as you're using JWT

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


//package com.library.books.security;
//
//import com.library.books.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfiguration {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // Disable CSRF protection, assuming JWT token is used for CSRF protection
//                .authorizeRequests(requests -> requests
//                        .requestMatchers("/", "/home", "/register", "/login", "/authenticate").permitAll()
//                        .requestMatchers("/logout").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .apply(jwtConfigurer());
//        return http.build();
//    }
//
//    private JwtConfigurer jwtConfigurer() {
//        return new JwtConfigurer(jwtAuthenticationFilter());
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        // Use the autowired customUserDetailsService and jwtTokenUtil beans
//        return new JwtAuthenticationFilter(customUserDetailsService, jwtTokenUtil);
//    }
//
//}
//




//
//package com.library.books.security;
//
//import com.library.books.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfiguration {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/", "/home", "/register",
//                                "/login", "/authenticate").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .apply(jwtConfigurer())
//                .and()
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout(logout -> logout.permitAll());
//
//
//        return http.build();
//    }
//
//    private JwtConfigurer jwtConfigurer() {
//        return new JwtConfigurer(jwtAuthenticationFilter());
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        // Use the autowired customUserDetailsService and jwtTokenUtil beans
//        return new JwtAuthenticationFilter(customUserDetailsService, jwtTokenUtil);
//    }
//
//}
