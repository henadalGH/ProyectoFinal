package com.example.wmotorproBack.wmotorBack.Controller;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.LoginDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;

import com.example.wmotorproBack.wmotorBack.Servicio.AuthService;
import com.example.wmotorproBack.wmotorBack.Servicio.RegistroService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private AuthService authService;

    @Autowired
    private RegistroService registroService;


    
    @PostMapping("/registro")
    public ResponseEntity<ResponceDTO> registro(@RequestBody UsuarioEntity usuario
    ) throws Exception {

        return new ResponseEntity<>(registroService.register(usuario), HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> login(@RequestBody LoginDTO loginRequest) throws Exception {
        
        HashMap<String, String> login = authService.login(loginRequest);
        if (login.containsKey("jwt")) {
            return new ResponseEntity<>(login, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
        }
    }
    
}