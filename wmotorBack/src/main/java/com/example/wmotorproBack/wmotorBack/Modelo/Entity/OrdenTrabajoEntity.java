package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.Date;
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
@Table(name = "orden_trabajo")
public class OrdenTrabajoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_trabajo")
    private Long id;

    @Column(name = "km_ingreso")
    private Integer kmIngreso;

    @Column(name = "fecha_emicion")
    private Date fechaEminsion;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "costo_total")
    private Double constoTotal;

    @Column(name = "tiempo_estinado")
    private String tiempoEstimado;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private VehiculoEntity vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private EmpleadoEntity empleado;

    @ManyToOne
    @JoinColumn(name = "id_presupuesto")
    private PresupuestoEntity presupuesto;

    @ManyToOne
    @JoinColumn(name = "id_estado_orden")
    private EstadoOrdenEntity estadoOrden;

    @OneToMany(mappedBy = "orden")
    private List<EvalucionServicioEntity> evalucion;
}
