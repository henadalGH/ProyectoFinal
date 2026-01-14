package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;

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

@Data
@Entity
@Table(name = "estado_turno", schema = "wmotorpro")
public class EstadoTurnosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_turo")
    private Long id;

    @Column(name = "estado_turno")
    @Enumerated(EnumType.STRING)
    private EstadoTurnoEnums estadoTurno;

    @OneToMany(mappedBy = "estado")
    private List<TurnoEntity> turno = new ArrayList<>();
}
