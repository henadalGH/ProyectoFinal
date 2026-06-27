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
    private Double total;
    private EstadoPresupuestoEnum estadoPresupuesto;
    private List<DetallePresupuestoDTO> detallePresupuesto;
    private Double iva;
    private Double subTotal;
    private Long idOrden;
    private Long idAdmin;
    private Long idVehiculo;
    

}
