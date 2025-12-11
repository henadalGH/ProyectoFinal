package com.example.wmotorproBack.wmotorBack.Modelo.Entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "servicio", schema = "wmotorpro")
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long id;


    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precion_base")
    private Double precion_base;

    @Column(name = "duracion_estimada")
    private String duracionEstimada;

    @OneToMany(mappedBy = "servicio")
    private List<TurnoEntity> turno = new ArrayList<>();
}
