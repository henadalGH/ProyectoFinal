package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "estado_orden")
public class EstadoOrdenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long id;

    @Column(name = "estado")
    private EstadoOrdenEnums estadoOrden;
}
