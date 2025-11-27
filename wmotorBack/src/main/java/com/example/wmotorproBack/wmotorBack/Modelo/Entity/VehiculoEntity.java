package com.example.wmotorproBack.wmotorBack.Modelo.Entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column (name = "id_vehculo")
    private Long id;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "anio")
    private String anio;

    @Column 
    private String patente;

    @Column
    private int kilometraje;

    @Column
    private String numeroChasis;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteEntity idCliente;

    @OneToMany(mappedBy = "vehiculo")
    private List<HistorialEntity> historial = new ArrayList<>();


}
