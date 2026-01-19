package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;
import com.example.wmotorproBack.wmotorBack.Servicio.MovimientoFinService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/movimiento")
public class MovimientosController {


    @Autowired
    private MovimientoFinService movimientoFinService;

    @PostMapping("/registro")
    public ResponseEntity<ResponceDTO> registrarMovimiento(@RequestBody MovimientoDTO movimiento, @RequestParam MovimientosEnum movimientosEnum) throws Exception {
        
        return new ResponseEntity<>(movimientoFinService.crearMovimiento(movimiento, movimientosEnum), HttpStatus.CREATED);
    }


    
    

}
