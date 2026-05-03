package com.example.wmotorproBack.wmotorBack.Modelo.Entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private Long numeroPresupuesto;

    @Column(name = "fechaValidez")
    private LocalDate fechaValidez;

    @Column(name = "fecha_Registro")
    private LocalDate fechaRegistro;

    @Column(name = "total")
    private Double total;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn( name = "id_administrador")
    private AdminEntity admin;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private VehiculoEntity vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private EstadoPresupuestoEntity estadoPresupuesto;

    @ManyToOne
    @JoinColumn(name = "id_turno")
    private TurnoEntity turno;

    @OneToMany(mappedBy = "presupuesto")
    private List<DetallePresupuestoEntity> detalle = new ArrayList<>();

    @OneToMany(mappedBy = "presupuesto")
    private List<OrdenTrabajoEntity> orden = new ArrayList<>();
}
