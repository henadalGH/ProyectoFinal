package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerPresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.PresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;
import com.example.wmotorproBack.wmotorBack.Servicio.PresupuestoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





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

    @GetMapping("/obtener/{idVehiculo}/vehiculo")
    public ResponseEntity<List<ObtenerPresupuestoDTO>> obtenerPresupuestoPorIdVehiculo(@PathVariable Long idVehiculo) {
        return new ResponseEntity<List<ObtenerPresupuestoDTO>>
        (presupuestoService.obtenerPresupuestoPorIdVehiculo(idVehiculo), HttpStatus.OK);
    }

    @PutMapping("cambiarEstado/{idPresupuesto}")
    public ResponseEntity<ResponceDTO> cambiarEstadoPresupuesto(@PathVariable Long idPresupuesto, @RequestBody EstadoPresupuestoEnum estado
    ) {
        return new ResponseEntity<ResponceDTO>(presupuestoService.cambiarEstadoPresupuesto(estado, idPresupuesto), HttpStatus.OK);
    }

    @GetMapping("/{idPresupuesto}")
    public ResponseEntity<ObtenerPresupuestoDTO> obtenerPorId(@PathVariable Long idPresupuesto) {
        return new ResponseEntity<ObtenerPresupuestoDTO>(presupuestoService.obtenerPresupuestoPorId(idPresupuesto), HttpStatus.OK);
    }
    
    
    

}
