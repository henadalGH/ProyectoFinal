package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;
import lombok.Data;

@Data
public class TurnosDTO {

    private Long id;
    private FechaDTO fecha;
    private String descripcion;
    private Long idVehiculo;
    private Long idCliente;
    private Long idServicio;
    private EstadoTurnoEnums estado;

}
