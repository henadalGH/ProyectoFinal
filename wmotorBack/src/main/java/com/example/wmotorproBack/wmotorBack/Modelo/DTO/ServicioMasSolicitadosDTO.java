package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class ServicioMasSolicitadosDTO {

    private String nombre;
    private Long cantidad;
    
    public ServicioMasSolicitadosDTO(String nombre, Long cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    
}
