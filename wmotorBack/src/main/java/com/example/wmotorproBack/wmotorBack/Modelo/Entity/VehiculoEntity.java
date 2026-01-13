package com.example.wmotorproBack.wmotorBack.Modelo.Entity;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name = "vehiculo" , schema = "wmotorpro", uniqueConstraints = {@UniqueConstraint(columnNames = {"patente"})})
public class VehiculoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_vehiculo")
    private Long id;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "anio")
    private Integer anio;

    @Column 
    private String patente;

    @Column
    private int kilometraje;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_cliente")
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
    private List<HistorialEntity> historial = new ArrayList<>();

    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
    private List<PresupuestoEntity> presupuesto = new ArrayList<>();

    @OneToMany(mappedBy = "vehiculo")
    private List<OrdenTrabajoEntity> orden = new ArrayList<>();

    @OneToMany(mappedBy = "vehiculo")
    private List<TurnoEntity> turno = new ArrayList<>();

}
