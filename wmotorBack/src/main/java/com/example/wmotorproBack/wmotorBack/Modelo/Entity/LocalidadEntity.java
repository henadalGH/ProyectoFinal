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
import lombok.Data;

@Data
@Entity
@Table(name = "localidad", schema = "wmotorpro")
public class LocalidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLocalidad")
    private Long id;
    
    @Column
    private String nombreLocalidad;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private ProvinciaEntity provincia;

    @OneToMany(mappedBy = "localidad")
    private List<ClienteEntity> cliente = new ArrayList<>();
}
