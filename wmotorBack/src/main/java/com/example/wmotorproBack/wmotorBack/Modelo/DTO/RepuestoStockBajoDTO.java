package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class RepuestoStockBajoDTO {

    private Long id;

    private String nombre;

    private String marca;

    private String codigo;

    private Long stock;

    private Long stockMin;


    public RepuestoStockBajoDTO(
            Long id,
            String nombre,
            String marca,
            String codigo,
            Long stock,
            Long stockMin
    ) {

        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.codigo = codigo;
        this.stock = stock;
        this.stockMin = stockMin;

    }
}
