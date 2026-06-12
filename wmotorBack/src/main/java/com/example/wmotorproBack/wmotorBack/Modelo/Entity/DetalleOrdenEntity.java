package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TipoItemEnums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "trabajo_realizado")
    private String trabajoRealizados;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "tipo_item")
    @Enumerated(EnumType.STRING)
    private TipoItemEnums tipoItem;

    @Column(name = "codigo")
    private String codigo;
    
    @ManyToOne
    @JoinColumn(name = "id_asignacion")
    private OrdenTrabajoEntity orden;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private ServicioEntity servicio;

}
