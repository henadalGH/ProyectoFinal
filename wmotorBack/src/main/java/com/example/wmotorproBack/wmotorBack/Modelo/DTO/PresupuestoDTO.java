package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;
import java.util.List;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;

import lombok.Data;

@Data
public class PresupuestoDTO {

    private Long id;
    private Long numeroPresupuesto;
    private LocalDate fechaValidez;
    private LocalDate fechaRegistro;
    private Integer total;
    private String obserbaciones;
    private EstadoPresupuestoEnum estadoPresupuesto;
    private List<DetallePresupuestoDTO> detallePresupuesto;
    private Long idAdmin;
    

}
