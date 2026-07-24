package com.example.wmotorproBack.wmotorBack.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @PostMapping("/crear")
    public ResponseEntity<TurnoResponseDTO> crearTurno(@RequestBody TurnosDTO turno) {

        TurnoEntity turnoGuardado = turnoService.creaTurnosDTO(turno);

        return ResponseEntity.ok(toResponseDTO(turnoGuardado));
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

        if (entity.getEstado() != null) {
            response.setEstado(entity.getEstado().getEstadoTurno().name());
        }

        return response;
    }

    @GetMapping("/obtenerEstado")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnosPorEstado(
            @RequestParam EstadoTurnoEnums estado) {

        return ResponseEntity.ok(turnoService.obtenerTurnosPorEstado(estado));
    }

    @PutMapping("/asignarFecha/{id}")
    public ResponseEntity<ResponceDTO> asignarFecha(
            @PathVariable Long id,
            @RequestBody FechaDTO fecha) {

        return ResponseEntity.ok(turnoService.asignarFecha(id, fecha));
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<ResponceDTO> actualizarEstadoTurno(
            @PathVariable Long id,
            @RequestBody EstadosDTO estado) {

        return ResponseEntity.ok(turnoService.actualizarEstadoTurno(id, estado));
    }

    @GetMapping("/obtenerPorVehiculo/{idVehiculo}")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnosPendientePorVehiculo(
            @PathVariable Long idVehiculo) {

        return ResponseEntity.ok(
                turnoService.obtenerturnoPorIdVehiculoEstado(idVehiculo));
    }

    @GetMapping("/obtenerTurnoCliente/{idCliente}")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnosPendientesPorCliente(
            @PathVariable Long idCliente) {

        return ResponseEntity.ok(
                turnoService.obtenerturnoPorIdClienteEstado(idCliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoEstadosDTO> obtenerTurnoPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                turnoService.obtenerTurnoPorId(id));
    }

    @GetMapping("/obtenerPorFecha")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerPorFecha(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDateTime fecha) {

        return ResponseEntity.ok(
                turnoService.obtenerTurnosPorFecha(fecha));
    }

    @GetMapping("/futuros")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnosFuturos(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDateTime fecha) {

        return ResponseEntity.ok(
                turnoService.obtenerTurnofuturos(fecha));
    }

    @GetMapping("/futuros/{idCliente}")
    public ResponseEntity<List<TurnoEstadosDTO>> obtenerTurnosFuturosPorCliente(
            @PathVariable Long idCliente,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDateTime fecha) {

        return ResponseEntity.ok(
                turnoService.obtenrTurnoFururosPorCliente(idCliente, fecha));
    }

    @GetMapping("/contarTurnoPendientes")
    public ResponseEntity<Long> contarTurnosPendientes() {

        return ResponseEntity.ok(
                turnoService.contarTurnosPendientes());
    }

    @GetMapping("/contarTurnoConirmados")
    public ResponseEntity<Long> contarTurnosConfirmados() {

        return ResponseEntity.ok(
                turnoService.contarTurnosConfirmados());
    }

    @GetMapping("/servicioMasSolicitados")
    public ResponseEntity<List<ServicioMasSolicitadosDTO>> obtenerServiciosMasSolicitados() {

        return ResponseEntity.ok(
                turnoService.obtenerServicioMasSolicitados());
    }

    @GetMapping("/turnosPorMes")
    public ResponseEntity<List<TurnosPorMesDTO>> obtenerTurnosPorMes() {

        return ResponseEntity.ok(
                turnoService.obtenerTurnosPorMes());
    }

    @GetMapping("/turnosPorEstado")
    public ResponseEntity<List<TurnosPorEstadoDTO>> obtenerTurnosPorEstadoCantidad() {

        return ResponseEntity.ok(
                turnoService.obtenerTurnosPorEstado());
    }

}