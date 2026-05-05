package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObtenerPresupuestoDTO {

    private Long id;
    private Long numeroPresupuesto;
    private LocalDate fechaVencimiesto;
    private LocalDate fechaRegistro;
    private Double total;
    private String observaciones;
    private EstadoPresupuestoEnum estadoPresupuesto;
    private List<DetallePresupuestoDTO> detallePresupuesto;
    private String nombreCliente;
    private String apellidoCliente;
    private String direccionCliente;
    private String correoCliente;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String nombreAdmin;
}