package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TurnoResponseDTO {
    private Long id;
    private String descripcion;
    private LocalDateTime fechaHora;
    private Long idVehiculo;
    private Long idServicio;
    private String estado;
}
