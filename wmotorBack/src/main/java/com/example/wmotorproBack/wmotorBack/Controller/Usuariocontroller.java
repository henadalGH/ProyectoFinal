package com.example.wmotorproBack.wmotorBack.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.UsuarioServicio;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/usuario")
public class Usuariocontroller {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioEntity>> getAlluser() {
        
        return new ResponseEntity<>(usuarioServicio.findAllUsers(), HttpStatus.OK);
    }
    
    
}
