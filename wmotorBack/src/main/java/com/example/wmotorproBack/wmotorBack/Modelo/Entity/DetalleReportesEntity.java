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
@Table(name = "detalleRepostes", schema = "wmotor")
public class DetalleReportesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "detalle")
    private String detalle;

    @ManyToOne
    @JoinColumn(name = "id_reportes")
    private ReportesEntity reportes;

    @ManyToOne
    @JoinColumn(name = "id_estadistica")
    private EstadisticasEntity estadisticas;
}
