package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;
import java.util.List;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;

import lombok.Data;

@Data
public class OrdenReparacionDTO {

    private Long id;
    private Long numeroOrden;
    private Long kilometroVehiculo;
    private LocalDate fechaEmicion;
    private LocalDate fechaCierre;
    private LocalDate fechaEntragaCliente;
    private EstadoOrdenEnums estado;
    private String motivoCancelacion;
    
    private List<DetalleOrdenDTO> detalleOrden;
    private Long idPrioridad;
    private Long idVehiculo;
    private Long idEmpleado;


}
