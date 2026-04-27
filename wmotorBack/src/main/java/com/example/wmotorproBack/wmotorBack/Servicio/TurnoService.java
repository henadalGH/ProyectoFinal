package com.example.wmotorproBack.wmotorBack.Servicio;



import java.time.LocalDateTime;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnoPendenteAsignacionDto;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;

public interface TurnoService {


    public TurnoEntity creaTurnosDTO(TurnosDTO turno);

    public List<TurnoPendenteAsignacionDto> obtenerTodosTurnosPendienteAsignacion();

    public TurnoPendenteAsignacionDto toMapTurnoDto(TurnoEntity turno);

    public TurnoEntity asignarFecha(Long idTurno, LocalDateTime fecha);

}
