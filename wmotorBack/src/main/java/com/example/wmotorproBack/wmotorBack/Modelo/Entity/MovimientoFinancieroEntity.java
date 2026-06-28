package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "movimientos_financieros", schema = "wmtorpro")
public class MovimientoFinancieroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private MovimientosEnum tipoMovimiento;

    @Column(name = "concepto", nullable = false)
    private String concepto;

    @Column(name = "importe", nullable = false)
    private Double importe;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin", nullable = false)
    private AdminEntity admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaMovimientoEntity categoria;
}