package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class DetallePresupuestoDTO {

    private Long id;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subTotal;
    private Long idVehiculo;
    private Long idAdministrador;
}
