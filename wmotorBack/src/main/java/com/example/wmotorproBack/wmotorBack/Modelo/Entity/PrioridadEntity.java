package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;

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
@Table(name = "priorodad", schema = "wmotorpro")
public class PrioridadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridad")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad")
    private PrioridadEnum prioridad;

    @OneToMany(mappedBy = "prioridad")
    private List<DetalleOrdenEntity> orden;
}
