package com.example.wMotorPro.WMotorPro.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cargo", schema = "WMotorPro")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Long id;

    @Column(name = "tipo_cargo", nullable = false, length = 100)
    private String nombre;

    @Column(name = "sueldo", nullable = false)
    private Integer sueldo;

    
}
