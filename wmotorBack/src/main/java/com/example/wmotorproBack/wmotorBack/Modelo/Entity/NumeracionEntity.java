package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "numeroPresupuesto")
public class NumeracionEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "valor")
    private Long valor;

}
