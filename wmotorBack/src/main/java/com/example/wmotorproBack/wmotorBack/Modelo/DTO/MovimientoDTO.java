package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.util.Date;


import lombok.Data;

@Data
public class MovimientoDTO {
    
    private Long id;
    private String tipoMovimiento;
    private String categoria;
    private String concepto;
    private Double importe;
    private Date fechaRegistro;
    private Long idAdmin;

    


}
