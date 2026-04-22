package com.example.wmotorproBack.wmotorBack.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.VehiculoService;

import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponceDTO> obtener(@PathVariable @NotNull Long id) {
    return ResponseEntity.ok(vehiculoService.obtenerVehiculoPorID(id));
    }
    
    @GetMapping("/buscar/cliente/{clienteId}")
    public ResponseEntity<?> obtenerVehiculoPorId(@PathVariable Long clienteId) {
        List<VehiculoDTO> lista = vehiculoService.ontenerVehiculoPorIdCliente(clienteId);

    if (lista.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No se encontraron vehículos para este usuario");
    }

    return ResponseEntity.ok(lista);
    }
    
    
    
}
