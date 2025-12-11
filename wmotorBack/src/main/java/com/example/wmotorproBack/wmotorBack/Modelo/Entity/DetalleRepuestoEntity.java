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
@Table(name = "detelle_repuesto", schema = "wmotorpro")
public class DetalleRepuestoEntity {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_detalle_repuesto")
private Long id;


@Column(name = "cantidad")
private Integer cantidad;

@Column(name = "costo_unitario")
private Double costoUnitario;

@ManyToOne
@JoinColumn(name = "id_historial")
private HistorialEntity historial;


}
