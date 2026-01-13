package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientosFinDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.MovimientoFinancieroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/finansa")
public class MovimientoFinancieroController {

    @Autowired
    private MovimientoFinancieroService movimientoService;

    @PostMapping("/agregar")
    public ResponseEntity<ResponceDTO> registrarDatos(@RequestBody MovimientosFinDTO movimmientoDto) throws Exception {
        return new ResponseEntity<>(movimientoService.registrarFinasas
            (movimmientoDto), HttpStatus.CREATED);
    }
    

}
