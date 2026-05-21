package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class VehiculoDTO {

    private Long id;
    private String marca;
    private String modelo;
    private String anio;
    private String patente;
    private Long Kilometraje;
    private Long idCliente;
    
}
