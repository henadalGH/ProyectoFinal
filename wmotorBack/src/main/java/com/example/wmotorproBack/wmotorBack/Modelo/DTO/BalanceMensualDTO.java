package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import lombok.Data;

@Data
public class BalanceMensualDTO {

    private int mes;
    private String nombreMes;
    private Double totalIngresos = 0.0;
    private Double totalGastos = 0.0;
    private Double balance = 0.0;

    public BalanceMensualDTO(int mes) {
        this.mes = mes;
        this.nombreMes = Month.of(mes)
                .getDisplayName(TextStyle.FULL, Locale.getDefault());
    }
}
