package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;

import lombok.Data;

@Data
public class TurnosPorEstadoDTO {

    private EstadoTurnoEnums estado;
    private Long cantidad;
    public TurnosPorEstadoDTO(EstadoTurnoEnums estado, Long cantidad) {
        this.estado = estado;
        this.cantidad = cantidad;
    }

    
}
