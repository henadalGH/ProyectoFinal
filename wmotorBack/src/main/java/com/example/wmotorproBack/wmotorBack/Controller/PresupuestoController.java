package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.PresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.PresupuestoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/presupuesto")
public class PresupuestoController {

    @Autowired
    private PresupuestoService presupuestoService;


    @PostMapping("crear")
    public ResponseEntity<ResponceDTO> crearPresupuesto(@RequestBody PresupuestoDTO presupuestoDTO) {

        ResponceDTO responce = presupuestoService.crearPresupuesto(presupuestoDTO);
        return ResponseEntity.ok(responce);
    }
    

}
