package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.util.Date;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;

import lombok.Data;

@Data
public class MovimientoDTO {
    
    private Long id;
    private MovimientosEnum tipoMovimiento;
    private String categoria;
    private String concepto;
    private Double importe;
    private Date fechaRegistro;

}
