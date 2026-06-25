package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;

import lombok.Data;

@Data
public class ObtenerOrdenDTO {

    //Datos de la orden
    private Long id;
    private Long numeroOrden;
    private LocalDate fechaEmicion;
    private LocalDate fechaCierre;
    private LocalDate fechaEntragaCliente;
    private EstadoOrdenEnums estado;
    private String motivoCancelacion;

    //Datos del vehiculo
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String patenteVehiculo;
    private Long kilometroVehiculo;
    private Long idVehiculo;

    //Datos del empleado
    private String nombreEmpleado;

    //Detalle de orden
    private List<DetalleOrdenDTO> detalleOrden;

    private PrioridadEnum prioridad;

    //Datos del cliente
    private String nombreCliente;
    private String contacto;
    private String email;
    

}
