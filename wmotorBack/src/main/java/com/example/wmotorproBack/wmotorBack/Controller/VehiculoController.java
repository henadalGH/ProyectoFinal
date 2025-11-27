package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.VehiculoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping("/agregar")
    public ResponseEntity<VehiculoEntity> postMethodName(@RequestBody VehiculoDTO vehiculoDTO) {
        VehiculoEntity vehiculo = vehiculoService.agregarVehiculo(vehiculoDTO);
        return ResponseEntity.ok(vehiculo);
    }
    
}
