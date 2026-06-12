package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.DetalleOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetalleOrdenEntity;

public interface DetalleOrdenService {

    ResponceDTO agregarDetalles(List< DetalleOrdenDTO> detalleOrden, Long idOrden);
    DetalleOrdenDTO toMapDetalleDTO(DetalleOrdenEntity detalleOrden);
    List<DetalleOrdenDTO> obtenerDetallePorIdOrden(Long idOrden);



}
