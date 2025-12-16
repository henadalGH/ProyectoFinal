package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cargos", schema = "wmotorpro")
public class CargosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "cargo")
    private CargosEnum cargo;

    @Column(name = "sueldo_base")
    private Double sueldoBase;

}
