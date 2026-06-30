package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;

import lombok.Data;

@Data
public class UltimosMovimientosDTO {

    private MovimientosEnum tipoMovimiento;
    private Double monto;
    private LocalDate fechaRegistro;
    private String categoria;



}
