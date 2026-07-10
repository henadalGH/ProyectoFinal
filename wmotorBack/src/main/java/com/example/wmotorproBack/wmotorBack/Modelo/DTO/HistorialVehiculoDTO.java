package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HistorialVehiculoDTO {

    private String nombreEmpleado;
    private String reparacionRealizada;
    private String repustos;
    private Long kilometroReparacion;
    private String observaciones;
    private LocalDate fechaReparacion;
    

}
