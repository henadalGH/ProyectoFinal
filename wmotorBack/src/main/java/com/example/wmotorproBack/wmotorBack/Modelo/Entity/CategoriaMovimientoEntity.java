package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria_movimiento")
public class CategoriaMovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "categoria")
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private MovimientosEnum movimientos;

    @OneToMany(mappedBy = "categoria")
    private List<MovimientoFinancieroEntity> movimiento = new ArrayList<>();
}