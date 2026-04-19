package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.TurnoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    
    @PostMapping("/crear")
    public ResponseEntity<TurnosDTO>postMethodName(@RequestBody TurnosDTO turno) {
        return new ResponseEntity<>( turnoService.creaTurnosDTO(turno), HttpStatus.OK);
    }
    
}
