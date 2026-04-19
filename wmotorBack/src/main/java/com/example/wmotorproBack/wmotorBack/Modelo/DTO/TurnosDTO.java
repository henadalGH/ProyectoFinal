package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;

import lombok.Data;

@Data
public class TurnosDTO {

    private LocalDate fecha;
    private String descripcion;
    private Long idVehiculo;
    private Long idCliente;
    private Long idServicio;
    private EstadoTurnoEnums estado;

}
