package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.time.LocalDate;

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
@Table(name = "movimientos_financieros", schema = "wmtorpro")
public class MovimientoFinancieroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long id;

    @Column(name = "concepto", nullable = false)
    private String concepto;

    @Column(name = "importe", nullable = false)
    private Double importe;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    // Relación con usuario/admin que registra el movimiento
    @ManyToOne
    @JoinColumn(name = "id_admin")
    private AdminEntity admin;

    // Categoría del movimiento (de aquí se obtiene el tipo INGRESO/EGRESO)
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaMovimientoEntity categoria;
}