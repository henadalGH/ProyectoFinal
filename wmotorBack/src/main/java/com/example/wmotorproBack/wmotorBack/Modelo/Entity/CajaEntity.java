package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "caja", schema = "wmotorpro")
public class CajaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caja")
    private Long id;


    @Column(name = "fecha_apertura")
    private Date fechaApertura;

    @Column(name = "fecha_cierre")
    private Date fechaCierre; 

    @Column(name = "saldo_inicial")
    private Double saldoInicial;

    @Column(name = "saldo_final")
    private Double saldoFinal;
}
