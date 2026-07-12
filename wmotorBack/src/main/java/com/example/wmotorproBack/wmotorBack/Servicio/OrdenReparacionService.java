package com.example.wmotorproBack.wmotorBack.Servicio;

import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.CancelarOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.OrdenReparacionDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.OrdenTrabajoEmpleadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;

public interface OrdenReparacionService {

    ResponceDTO crearReparacion(OrdenReparacionDTO orden);
    List<ObtenerOrdenDTO> obtenerTodaLasOrdenes();
    ObtenerOrdenDTO obtnerOrdenPorId(Long id);
    ResponceDTO actualizarEstaOrden(EstadoOrdenEnums estado, Long id);
    List<ObtenerOrdenDTO> obtenerOrdenPorEstado(EstadoOrdenEnums estado);
    ObtenerOrdenDTO toMapObtenerOrdenDTO(OrdenTrabajoEntity ordenTrabajo);
    ResponceDTO asignarOrdeEmpleado(Long idTurno, Long idEmpleado, PrioridadEnum prioridad);
    List<OrdenTrabajoEmpleadoDTO> obtenerOrdenePorEmpleado(Long idEmpleado, LocalDate fecha);
    OrdenTrabajoEmpleadoDTO toOrdenTrabajoEmpleadoDTO(OrdenTrabajoEntity orden);
    ResponceDTO motivoCancelacion(Long idOrden, CancelarOrdenDTO cancelar);
    List<OrdenTrabajoEmpleadoDTO> ordenesfuturasIdempleado(Long idEmpleado, LocalDate fecha);
   

}
