package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

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
@Table(name = "detalle_ordenes", schema = "wmotorpro")
public class DetalleOrdenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_orden")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "id_asignacion")
    private OrdenTrabajoEntity orden;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private ServicioEntity servicio;

    @ManyToOne
    @JoinColumn(name = "id_prioridad")
    private PrioridadEntity prioridad;

}
