package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TipoItemEnums;

import lombok.Data;

@Data
public class DetallePresupuestoDTO {

    private Long id;
    private String descripcion;
    private Integer cantidad;
    private Double prescioUnitario;
    private TipoItemEnums tipoItem;
    private Double subTotal;
    private Double hora;
    private String codigo;
}
