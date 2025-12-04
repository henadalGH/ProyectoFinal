package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private String tipo_movimiento;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "importe")
    private Integer importe;

    @Column(name = "recha_registro")
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_admin")
    private AdminEntity admin;
}
