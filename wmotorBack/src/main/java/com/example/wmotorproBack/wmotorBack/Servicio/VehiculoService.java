package com.example.wmotorproBack.wmotorBack.Servicio;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;

public interface VehiculoService {

    public ResponceDTO agregarVehiculo(VehiculoDTO vehiculoDTO, Long id)throws Exception;
    
    ResponceDTO obtenerVehiculoPorID(Long id);

    public Boolean eliminarVehiculo(Long id);


    public void darDeBajaVehiculo(Long id_vehilo, Long id_cliente);
}
