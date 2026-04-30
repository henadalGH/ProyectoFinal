package com.example.wmotorproBack.wmotorBack.Servicio;

import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnoEstadosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;

public interface TurnoService {


    public TurnoEntity creaTurnosDTO(TurnosDTO turno);

    public List<TurnoEstadosDTO> obtenerTurnosPorEstado(EstadoTurnoEnums estado);

    public TurnoEstadosDTO toMapTurnoDto(TurnoEntity turno);

    public ResponceDTO asignarFecha(Long idTurno, LocalDate fecha);

    public ResponceDTO actualizarEstadoTurno(Long idTurno, EstadoTurnoEnums estadoEnum);


}
