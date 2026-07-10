package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;

import lombok.Data;

@Data
public class UltimasFacturasDTO {

    private Long numeroFactura;
    private String nombreCliente;
    private Double monto;
    private EstadoPresupuestoEnum estado;
    private LocalDate fechaRegistro;
}
