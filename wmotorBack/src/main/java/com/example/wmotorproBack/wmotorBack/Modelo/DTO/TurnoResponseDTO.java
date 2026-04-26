package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TurnoResponseDTO {
    private Long id;
    private String descripcion;
    private LocalDate fechaHora;
    private Long idVehiculo;
    private Long idServicio;
    private String estado;
}
