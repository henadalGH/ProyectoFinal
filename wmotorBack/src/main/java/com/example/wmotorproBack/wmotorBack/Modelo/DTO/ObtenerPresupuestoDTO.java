package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TipoFacturaDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObtenerPresupuestoDTO {

    private Long id;
    private Long numeroPresupuesto;
    private LocalDate fechaVencimiesto;
    private LocalDate fechaRegistro;
    private TipoFacturaDTO tipoFacturaDTO;
    private Double total;
    private Double subTotal;
    private Double iva;
    private String observaciones;
    private EstadoPresupuestoEnum estadoPresupuesto;
    private List<DetallePresupuestoDTO> detallePresupuesto;
    private String nombreCliente;
    private String apellidoCliente;
    private String direccionCliente;
    private String correoCliente;
    private String contactoCliente;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String patenteVehiculo;
    private String nombreAdmin;
} 