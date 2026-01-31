package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "movimientosFinancieros", schema = "wmtorpro")
public class MovimientoFinancieroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moviento")
    private Long id;

    @Column(name = "tipo_movimiento")
    @Enumerated(EnumType.STRING)
    private MovimientosEnum tipomovimiento;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "importe")
    private Double importe;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_admin")
    private AdminEntity admin;
}
