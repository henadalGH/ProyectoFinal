package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnoResponseDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.TurnoService;
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
    
}
