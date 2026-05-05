package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerPresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.PresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;

public interface PresupuestoService {

    public ResponceDTO crearPresupuesto(PresupuestoDTO presupuestoDTO);
    
    public ResponceDTO cambiarEstadoPresupuesto(EstadoPresupuestoEnum estado);

    public List<ObtenerPresupuestoDTO> obtenerPresupuestoPorIdVehiculo(Long id);

    public ObtenerPresupuestoDTO toMapPresupuestoDto(PresupuestoEntity presupuesto);

}
