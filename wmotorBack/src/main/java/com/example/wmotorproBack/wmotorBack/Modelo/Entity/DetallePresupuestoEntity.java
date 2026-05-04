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
@Table(name = "detalle_presupuesto", schema = "wmotorpro")
public class DetallePresupuestoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;

    @Column(name = "descricion")
    private String descripcion;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "tipo_item")
    @Enumerated(EnumType.STRING)
    private TipoItemEnums tipo;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "codigo")
    private String codigo;

    @Column( name = "horas")
    private Double horas;

    @ManyToOne
    @JoinColumn(name = "id_presupuesto")
    private PresupuestoEntity presupuesto;
}
