package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.OrdenReparacionDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.OrdenReparacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/orden")
public class OrdenReparacionController {

    @Autowired
    private OrdenReparacionService ordenReparacionService;

    @PostMapping("/crearOrden")
    public ResponseEntity<ResponceDTO> crearOrden(@RequestBody OrdenReparacionDTO ordenReparacionDTO) {
        
        return new ResponseEntity<ResponceDTO>(ordenReparacionService.crearReparacion(ordenReparacionDTO), HttpStatus.OK);
    }
    

}
