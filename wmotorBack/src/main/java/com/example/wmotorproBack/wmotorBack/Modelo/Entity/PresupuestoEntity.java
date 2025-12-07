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
@Table(name = "presupuesto", schema = "wmotorpro")
public class PresupuestoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presupuesto")
    private Long id;

    @Column(name = "numero_presupuesto")
    private String numeroPresupuesto;

    @Column(name = "fecha_Registro")
    private String fechaRegistro;

    @ManyToOne
    @JoinColumn( name = "id_administrador")
    private AdminEntity admin;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private VehiculoEntity vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private EstadoPresupuestoEntity estadoPresupuesto;
}
