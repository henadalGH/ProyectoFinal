package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ObtenerOrdenReasignarDTO {

    private Long id;
    private String nombreCompletoEmpelado;
    private LocalDate fecha;
    private String nombreServicio;
    private String vehiculo;
    private String patente;
    
}
