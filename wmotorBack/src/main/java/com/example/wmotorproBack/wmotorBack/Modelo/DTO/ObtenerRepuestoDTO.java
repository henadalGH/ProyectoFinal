package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data

public class ObtenerRepuestoDTO {

    private Long id;
    private String nombreRepuesto;
    private Long cantidad;
    private String marcaRepuesto;
    private String codigo;
    
    public ObtenerRepuestoDTO(Long id, String nombreRepuesto, Long cantidad, String marcaRepuesto, String codigo) {
        this.id = id;
        this.nombreRepuesto = nombreRepuesto;
        this.cantidad = cantidad;
        this.marcaRepuesto = marcaRepuesto;
        this.codigo = codigo;
    }

    

}
