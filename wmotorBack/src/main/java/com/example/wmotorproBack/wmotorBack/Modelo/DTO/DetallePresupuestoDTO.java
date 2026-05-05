package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TipoItemEnums;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class DetallePresupuestoDTO {

    private Long id;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private TipoItemEnums tipoItem;
    private Double subTotal;
    private Double hora;
    private String codigo;
    private Long idVehiculo;
    private Long idCliente;
    private Long idAdministrador;
    private Long idTurno;
}
