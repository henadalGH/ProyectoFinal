package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CategoriasRepuestoEnums;
import lombok.Data;

@Data
public class RepuestoDTO {

    private Long id;
    private String nombre;
    private String marca;
    private String codigo;
    private Long stock;
    private Long stockMin;
    private Double precioCompra;
    private Double precioVenta;
    private Boolean activo;
    private CategoriasRepuestoEnums categoria;

}
