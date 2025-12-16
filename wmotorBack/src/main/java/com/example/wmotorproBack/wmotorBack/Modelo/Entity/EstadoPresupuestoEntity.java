package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;

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
@Table(name = "estado_presupuesto", schema = "wmotorpro")
public class EstadoPresupuestoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_presupuesto")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estado")
    private EstadoPresupuestoEnum estadoPresupuesto;

    @OneToMany(mappedBy = "estadoPresupuesto")
    private List<PresupuestoEntity> presupuesto = new ArrayList<>();
}
