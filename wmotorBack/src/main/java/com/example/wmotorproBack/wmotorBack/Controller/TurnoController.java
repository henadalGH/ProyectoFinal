package com.example.wmotorproBack.wmotorBack.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.EstadosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.FechaDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ServicioMasSolicitadosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnoEstadosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnoResponseDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosPorEstadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosPorMesDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;
import com.example.wmotorproBack.wmotorBack.Servicio.TurnoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    
    
    @PostMapping("/crear")
    public ResponseEntity<TurnoResponseDTO> crearTurno(@RequestBody TurnosDTO turno) {
        TurnoEntity turnoGuardado = turnoService.creaTurnosDTO(turno);
        return new ResponseEntity<>(toResponseDTO(turnoGuardado), HttpStatus.OK);
    }

    private TurnoResponseDTO toResponseDTO(TurnoEntity entity) {
        if (entity == null) {
            return null;
        }

        TurnoResponseDTO response = new TurnoResponseDTO();
        response.setId(entity.getId());
        response.setDescripcion(entity.getDescripcion());
        response.setFechaHora(entity.getFechaHora());

        if (entity.getVehiculo() != null) {
            response.setIdVehiculo(entity.getVehiculo().getId());
        }
        if (entity.getServicio() != null) {
            response.setIdServicio(entity.getServicio().getId());
        }
        if (entity.getEstado() != null && entity.getEstado().getEstadoTurno() != null) {
            response.setEstado(entity.getEstado().getEstadoTurno().name());
        }

        return response;
    }


    @GetMapping("/obtenerEstado")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnosPendienteAsignacion(@RequestParam EstadoTurnoEnums estado) {
        return new ResponseEntity<List<TurnoEstadosDTO>>(turnoService.obtenerTurnosPorEstado(estado), HttpStatus.OK);
    }
    
    
    @PutMapping("/asignarFecha/{id}") 
    public ResponseEntity<ResponceDTO> asignarFecha(@PathVariable Long id, @RequestBody FechaDTO fecha) {
        ResponceDTO turnoActualizado = turnoService.asignarFecha(id, fecha);
        System.out.print(fecha);
        return new ResponseEntity<ResponceDTO>(turnoActualizado, HttpStatus.OK);
    }
    

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<ResponceDTO> actualizarEstadoTurno(@PathVariable Long id, @RequestBody EstadosDTO estado) {
        ResponceDTO respoce = turnoService.actualizarEstadoTurno(id, estado);
        return ResponseEntity.ok(respoce);
    }

    @GetMapping("/obtenerPorVehiculo/{idVehiculo}")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnosPendientePorIdVehiculo(@PathVariable Long idVehiculo) {
        return new ResponseEntity<List<TurnoEstadosDTO>>(turnoService.obtenerturnoPorIdVehiculoEstado(idVehiculo), HttpStatus.OK);
    }

    @GetMapping("/obtenerTurnoCliente/{idCliente}")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnoPendientesPorCliente(@PathVariable Long idCliente) {
        return new ResponseEntity<List<TurnoEstadosDTO>>(turnoService.obtenerturnoPorIdClienteEstado(idCliente), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TurnoEstadosDTO> obtenerTurnoPorId(@PathVariable Long id) {
        return new ResponseEntity<TurnoEstadosDTO>(turnoService.obtenerTurnoPorId(id), HttpStatus.OK);
    }

    @GetMapping("/obtenerPorFecha")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        return ResponseEntity.ok(
                turnoService.obtenerTurnosPorFecha(fecha)
        );
    }

    @GetMapping("/futuros")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnoFuturos(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return new ResponseEntity<List<TurnoEstadosDTO>>(turnoService.obtenerTurnofuturos(fecha), HttpStatus.OK);
    }
    

    @GetMapping("/futuros/{idCliente}")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTrunoFuturosPorCliente(@PathVariable Long idCliente,
         @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
            return new ResponseEntity<List<TurnoEstadosDTO>>(turnoService.obtenrTurnoFururosPorCliente(idCliente, fecha), HttpStatus.OK);
    }

    @GetMapping("/contarTurnoPendientes")
    public ResponseEntity<Long> contarTurnoPendiente() {
        return new ResponseEntity<Long>(turnoService.contarTurnosPendientes(), HttpStatus.OK); 
    }

    @GetMapping("/contarTurnoConirmados")
    public ResponseEntity<Long> contarTurnoConfirmados() {
        return new ResponseEntity<Long>(turnoService.contarTurnosConfirmados(), HttpStatus.OK); 
    }

    @GetMapping("/servicioMasSolicitados")
    public ResponseEntity<List<ServicioMasSolicitadosDTO>> ontenerServicioMasSolicitaods() {
        return new ResponseEntity<List<ServicioMasSolicitadosDTO>>(turnoService.obtenerServicioMasSolicitados(),HttpStatus.OK);
    }

    @GetMapping("/turnosPorMes")
    public ResponseEntity<List<TurnosPorMesDTO>> contarTrunosPorMes() {
        return new ResponseEntity<List<TurnosPorMesDTO>>(turnoService.obtenerTurnosPorMes(), HttpStatus.OK);
    }

    @GetMapping("/turnosPorEstado")
    public ResponseEntity<List<TurnosPorEstadoDTO>> contarTrunosPorEstado() {
        return new ResponseEntity<List<TurnosPorEstadoDTO>>(turnoService.obtenerTurnosPorEstado(), HttpStatus.OK);
    }


}
