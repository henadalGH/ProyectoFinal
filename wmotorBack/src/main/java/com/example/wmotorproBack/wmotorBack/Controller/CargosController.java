package com.example.wmotorproBack.wmotorBack.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.CargoService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/cargos")
public class CargosController {

    @Autowired
    private CargoService cargoService;


    @GetMapping("/obtener")
    public ResponseEntity<List<CargosEntity>> getCargos() {
        return new ResponseEntity<>(cargoService.obtenerCargos(),HttpStatus.OK);

    }
    
}
