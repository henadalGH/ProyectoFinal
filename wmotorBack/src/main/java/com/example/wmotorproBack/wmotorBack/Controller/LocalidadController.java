package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerLocalidadDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.LocalidadService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/localidad")
public class LocalidadController {

    @Autowired
    private LocalidadService localidadService;

    @GetMapping("/obtener")
    public ResponseEntity<List<ObtenerLocalidadDTO>> obtenerLocalidad() {
        return new ResponseEntity<List<ObtenerLocalidadDTO>>(localidadService.obtenerLocalidade(), HttpStatus.OK);
    }
    

}
