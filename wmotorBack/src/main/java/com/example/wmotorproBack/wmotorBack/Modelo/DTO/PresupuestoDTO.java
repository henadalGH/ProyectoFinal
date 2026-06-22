package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;
import java.util.List;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TipoFacturaDTO;

import lombok.Data;

@Data
public class PresupuestoDTO {

    private Long id;
    private Long numeroPresupuesto;
    private LocalDate fechaValidez;
    private LocalDate fechaRegistro;
    private TipoFacturaDTO tipoFactura;
    private Integer total;
    private String observaciones;
    private EstadoPresupuestoEnum estadoPresupuesto;
    private List<DetallePresupuestoDTO> detallePresupuesto;
    private Long idAdmin;
    private Long idVehiculo;
    

}
