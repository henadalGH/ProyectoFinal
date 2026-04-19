package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class ServiciosDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precioBase;
    private String duracion;

}
