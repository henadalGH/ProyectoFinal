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
@Table(name = "orden_trabajo", schema = "wmotorpro")
public class OrdenTrabajoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Long id;

    @Column(name = "numero_orden")
    private Long nuemeroOrden;

    @Column(name = "km_ingreso")
    private Long kmIngreso;

    @Column(name = "fecha_emicion")
    private LocalDate fechaEminsion;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "fecha_entrega_cliente")
    private LocalDate fechaEntragaCliente;

    @Column(name = "motivo_cancelacion")
    private String motivoCancelacion;

    @ManyToOne
    @JoinColumn(name = "id_prioridad")
    private PrioridadEntity prioridad;


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

    @ManyToOne
    @JoinColumn(name = "id_historial")
    private HistorialEntity historial;

    @OneToMany(mappedBy = "orden")
    private List<EvalucionServicioEntity> evalucion = new ArrayList<>();

    @OneToMany(mappedBy = "orden")
    private List<DetalleOrdenEntity> detalleOrden = new ArrayList<>();
}
