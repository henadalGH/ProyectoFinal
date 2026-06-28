package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria_movimiento", schema = "wmtorpro")
public class CategoriaMovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "categoria", nullable = false, unique = true)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private MovimientosEnum tipoMovimiento;

    @OneToMany(mappedBy = "categoria")
    private List<MovimientoFinancieroEntity> movimientos = new ArrayList<>();
}