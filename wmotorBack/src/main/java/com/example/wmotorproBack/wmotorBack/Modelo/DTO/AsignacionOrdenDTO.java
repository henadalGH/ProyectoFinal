package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;

import lombok.Data;

@Data
public class AsignacionOrdenDTO {

    private Long idTurno;
    private Long idEmpleado;
    private LocalDate fechaCreacion;
    private EstadoOrdenEnums estado;
}
