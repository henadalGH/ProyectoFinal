package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;

import lombok.Data;

@Data
public class TurnoClienteCasualDTO {

    private Long id;
    private String nombreCliente;
    private String contactoCliente;
    private String emailCliente;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String patenteVehiculo;
    private TurnoEntity turno;

}
