package com.example.wmotorproBack.wmotorBack.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.EmpleadoService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/todos")
    public ResponseEntity<List<EmpleadoEntity>> getAllEmpleado() {
        return new ResponseEntity<>(empleadoService.getAllEmpleado(), HttpStatus.OK);
    }
    

}
