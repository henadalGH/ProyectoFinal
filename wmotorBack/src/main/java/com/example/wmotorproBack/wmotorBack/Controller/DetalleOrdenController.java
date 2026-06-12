package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.DetalleOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.DetalleOrdenService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/detalleOrden")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService detalleOrdenService;


    @PostMapping("/agregarDetalle/{idOrden}")
    public ResponseEntity<ResponceDTO> agregarDetalles(@RequestBody List<DetalleOrdenDTO> detalles, Long idOrden ) {
        return new ResponseEntity<ResponceDTO>(detalleOrdenService.agregarDetalles(detalles, idOrden), HttpStatus.OK);
    }

    @GetMapping("/obtenerDetalle/{idOrden}")
    public ResponseEntity<List<DetalleOrdenDTO>> obtenerDetalleOrdenPorIdOrden(@PathVariable Long idOrden) {
        return new ResponseEntity<List<DetalleOrdenDTO>>(detalleOrdenService.obtenerDetallePorIdOrden(idOrden),HttpStatus.OK);
    }
    
}
