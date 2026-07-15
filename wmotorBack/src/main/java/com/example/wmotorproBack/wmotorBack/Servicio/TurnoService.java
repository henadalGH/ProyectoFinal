package com.example.wmotorproBack.wmotorBack.Servicio;

import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.EstadosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.FechaDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ServicioMasSolicitadosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnoEstadosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;

public interface TurnoService {


    public TurnoEntity creaTurnosDTO(TurnosDTO turno);

    public List<TurnoEstadosDTO> obtenerTurnosPorEstado(EstadoTurnoEnums estado);

    public TurnoEstadosDTO toMapTurnoDto(TurnoEntity turno);

    public ResponceDTO asignarFecha(Long idTurno, FechaDTO fecha);

    public ResponceDTO actualizarEstadoTurno(Long idTurno, EstadosDTO estado);

    public List<TurnoEstadosDTO> obtenerturnoPorIdVehiculoEstado(Long id);

    List<TurnoEstadosDTO> obtenerturnoPorIdClienteEstado(Long idCliente);

    public TurnoEstadosDTO obtenerTurnoPorId(Long id);

    public List<TurnoEstadosDTO> obtenerTurnosPorFecha(java.time.LocalDate fecha);

    public List<TurnoEstadosDTO> obtenerTurnofuturos(LocalDate fecha);
    public List<TurnoEstadosDTO> obtenrTurnoFururosPorCliente(Long id, LocalDate fecha);
    public Long contarTurnosPendientes();
    public Long contarTurnosConfirmados();

    public List<ServicioMasSolicitadosDTO> obtenerServicioMasSolicitados();

}
