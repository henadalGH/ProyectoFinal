package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class ModificarPrecioPorMarca {

    private String marcaRepuesto;
    private String nombreRepuesto;
    private Integer precioCompra;
    private Integer porcentaje;


}
