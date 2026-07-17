package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CategoriasRepuestoEnums;

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
@Table(name = "repuesto", schema = "wmotorpro")
public class RepuestosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repuesto")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "marca")
    private String marca;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "stock_minimo")
    private Long stockMin;

    @Column(name = "precio_compra")
    private Double precioCompra;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "categoria_repuesto")
    @Enumerated(EnumType.STRING)
    private CategoriasRepuestoEnums categoria;


}
 