package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TurnoEstadosDTO {

    private Long id;
    private LocalDate fecha;
    private String descripcion;
    private Long idVehiculo;
    private String Marca;
    private String Modelo;
    private String nombreCliente;
    private String apellidoCliente;
    private String contacto;
    private String nombreServicio;
}
