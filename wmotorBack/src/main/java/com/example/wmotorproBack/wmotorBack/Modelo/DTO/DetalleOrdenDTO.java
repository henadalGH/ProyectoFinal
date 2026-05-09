package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TipoItemEnums;

import lombok.Data;

@Data
public class DetalleOrdenDTO {

    private String trabajoRealizado;
    private Long cantidad;
    private TipoItemEnums tipoItem;
    private String codigo;
    private String observaciones;
    
}
