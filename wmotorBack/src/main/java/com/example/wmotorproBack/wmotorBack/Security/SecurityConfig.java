package com.example.wmotorproBack.wmotorBack.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.wmotorproBack.wmotorBack.Servicio.JWTUtilityService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    
    @Autowired
    private JWTUtilityService jwtUtilityService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authRequest ->
            authRequest
                .requestMatchers("/auth/login").permitAll()   // pública
                    .requestMatchers("/auth/registro").permitAll() 
                .requestMatchers("/usuario/**").permitAll()
                .anyRequest().authenticated()
        )
        .sessionManagement(sessionM ->
            sessionM.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(
            new JWTAuthorizationFilter(jwtUtilityService),
            UsernamePasswordAuthenticationFilter.class
        )
        .exceptionHandling(exceptionHandling ->
            exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
            })
        )
        .build();
}

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

