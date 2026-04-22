package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class VehiculoDTO {

    private Long id;
    private String marca;
    private String modelo;
    private String anio;
    private String patente;
    private Integer Kilometraje;
    private Long idCliente;
    
    public VehiculoDTO(Long id, String marca, String modelo, String anio, String patente, Integer kilometraje) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.patente = patente;
        Kilometraje = kilometraje;
    }

    
   
}
