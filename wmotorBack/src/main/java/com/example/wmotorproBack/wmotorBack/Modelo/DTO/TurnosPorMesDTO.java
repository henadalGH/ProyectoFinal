package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class TurnosPorMesDTO {

    private Integer mes;
    private Long cantidad;
    
    public TurnosPorMesDTO(Integer mes, Long cantidad) {
        this.mes = mes;
        this.cantidad = cantidad;
    }

    
}
