package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class ObtenerNombreMarcaRepuestoDTO {

    private String nombreRepuesto;
    private String marcaRepuesto;

    public ObtenerNombreMarcaRepuestoDTO(String nombreRepuesto, String marcaRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
        this.marcaRepuesto = marcaRepuesto;
    }
}