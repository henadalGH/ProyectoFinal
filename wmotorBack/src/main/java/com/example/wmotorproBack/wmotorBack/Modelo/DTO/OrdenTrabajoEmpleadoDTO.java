package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;

import lombok.Data;

@Data
public class OrdenTrabajoEmpleadoDTO {

    //Poner datos del cliente
    private String nombreCliente;
    private String contacto;
    private String email;


    //Poner datos del Vehiculo
    private String marca;
    private String modelo;
    private String patente;
    private Long kiometraje;


    //poner datos del servicio a realizar
    private String nombreServicio;
    private String descripcionProblema;


    //Datos de la orden 
    private PrioridadEnum prioridad;

}
