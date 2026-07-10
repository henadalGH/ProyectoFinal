package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.CancelarOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.OrdenReparacionDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.OrdenTrabajoEmpleadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;
import com.example.wmotorproBack.wmotorBack.Servicio.OrdenReparacionService;


import java.time.LocalDate;
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
@RequestMapping("/orden")
public class OrdenReparacionController {

    @Autowired
    private OrdenReparacionService ordenReparacionService;

    @PostMapping("/crearOrden")
    public ResponseEntity<ResponceDTO> crearOrden(@RequestBody OrdenReparacionDTO ordenReparacionDTO) {
        
        return new ResponseEntity<ResponceDTO>(ordenReparacionService.crearReparacion(ordenReparacionDTO), HttpStatus.OK);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<ObtenerOrdenDTO>> obtenesTodaslasOrdenes() {
        return new ResponseEntity<List<ObtenerOrdenDTO>>(ordenReparacionService.obtenerTodaLasOrdenes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerOrdenDTO> obtenerOrdenPorId(@PathVariable Long id) {
        ObtenerOrdenDTO orden = ordenReparacionService.obtnerOrdenPorId(id);
        if (orden == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orden, HttpStatus.OK);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ResponceDTO> actualizarEstado(@PathVariable Long id, @RequestBody EstadoOrdenEnums estado) {
        ResponceDTO response = ordenReparacionService.actualizarEstaOrden(estado, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ObtenerOrdenDTO>> obtenerOrdenesPorEstado(@PathVariable EstadoOrdenEnums estado) {
        List<ObtenerOrdenDTO> ordenes = ordenReparacionService.obtenerOrdenPorEstado(estado);
        return new ResponseEntity<>(ordenes, HttpStatus.OK);
    }

    @PostMapping("asignacion/{idTurno}/{idEmpleado}/{prioridad}")
    public ResponseEntity<ResponceDTO> asignarOrdenEmpleado(@PathVariable PrioridadEnum prioridad, 
        @PathVariable Long idTurno,
        @PathVariable Long idEmpleado) {
        
        return new ResponseEntity<ResponceDTO>(ordenReparacionService.asignarOrdeEmpleado(idTurno, idEmpleado, prioridad), HttpStatus.OK);
    }


    @GetMapping("/obtenerOrdenesEmpledo/{idEmpleado}")
    public ResponseEntity<List<OrdenTrabajoEmpleadoDTO>> obtenerOrdenesPorEmpleado(@PathVariable Long idEmpleado
        , @RequestParam LocalDate fecha
    ) {
        return new ResponseEntity<List<OrdenTrabajoEmpleadoDTO>>(ordenReparacionService.obtenerOrdenePorEmpleado(idEmpleado, fecha), HttpStatus.OK);
    }


    @PutMapping("/cancelar/{idOrden}")
    public ResponseEntity<ResponceDTO> cancelarOrden(@PathVariable Long idOrden, @RequestBody CancelarOrdenDTO cancelar) {

        return new ResponseEntity<ResponceDTO>(ordenReparacionService.motivoCancelacion(idOrden, cancelar), HttpStatus.OK);
    }
    
    
    

}
