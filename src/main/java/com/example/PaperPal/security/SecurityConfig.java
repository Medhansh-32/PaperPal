package com.example.PaperPal.security;

import com.example.PaperPal.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, CustomOAuth2UserService customOAuth2UserService) {
        this.userDetailsService = userDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/user/**").permitAll()// Allow registration without authentication
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()// Require authentication for other requests
                )
              //  .httpBasic(Customizer.withDefaults())

                .formLogin(form -> form
                        .loginPage("/login") // Specify custom login page
                        .permitAll() // Allow all users to access the login page
                )
                .oauth2Login(outh2->
                         outh2.loginPage("/login")
                                .userInfoEndpoint(service->
                                        service.userService(customOAuth2UserService))
                )
                .logout(logout -> logout
                        .permitAll() // Allow all users to log out
                );

        return http.build(); // Build and return the SecurityFilterChain
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder()); // Use the password encoder bean
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Create and return a PasswordEncoder bean
    }
}
