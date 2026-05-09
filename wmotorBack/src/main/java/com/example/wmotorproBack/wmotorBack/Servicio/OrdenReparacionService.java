package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.OrdenReparacionDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;

public interface OrdenReparacionService {

    ResponceDTO crearReparacion(OrdenReparacionDTO orden);
    List<ObtenerOrdenDTO> obtenerTodaLasOrdenes();
    ObtenerOrdenDTO obtnerOrdenPorId(Long id);
    ResponceDTO actualizarEstaOrden(EstadoOrdenEnums estado, Long id);
    ObtenerOrdenDTO obtenerPorIdVehiculo(Long idVehiculo);
    List<ObtenerOrdenDTO> obtenerOrdenPorEstado(EstadoOrdenEnums estado);
    ObtenerOrdenDTO toMapObtenerOrdenDTO(OrdenTrabajoEntity ordenTrabajo);

   

}
