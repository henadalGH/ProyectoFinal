package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "evalucion_sericio", schema = "wmotorpro")
public class EvalucionServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evalucion")
    private Long id;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @Column(name = "comentario")
    private String comentario;

    @Column
    private Date fecha;

    @ManyToOne
    @JoinColumn(name =  "id_cliente")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "id_orden")
    private OrdenTrabajoEntity orden;
}
