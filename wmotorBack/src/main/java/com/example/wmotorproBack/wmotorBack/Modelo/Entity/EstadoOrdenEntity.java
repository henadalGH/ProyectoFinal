package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;

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
@Table(name = "estado_orden", schema = "wmotorpro")
public class EstadoOrdenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long id;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoOrdenEnums estadoOrden;

    @OneToMany(mappedBy = "estadoOrden")
    private List<OrdenTrabajoEntity> orden = new ArrayList<>();
}
