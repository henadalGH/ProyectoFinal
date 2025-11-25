package com.example.wmotorproBack.wmotorBack.Confing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.wmotorproBack.wmotorBack.Modelo.Validation.UsuarioValidacion;

@Configuration
public class ValidationConfig {

    @Bean
    UsuarioValidacion usuarioValidacion()
    {
        return new UsuarioValidacion();
    }
    
}
