package com.example.wmotorproBack.wmotorBack.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RepuestosEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.RepuestoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/repuesto") 
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;

    @GetMapping("/todos")
    public ResponseEntity<List<RepuestosEntity>> getRepuestos() {
        return new ResponseEntity<>(repuestoService.getAllRepuesto(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepuestoDTO> getRepustoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(repuestoService.getRepuestoPorId(id), HttpStatus.OK);
    }
    
    

}
