package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.Date;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TiposReporteEnums;

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
@Table(name = "Reportes", schema = "wmotorPro")
public class ReportesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reportes")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "tiposReportes")
    private TiposReporteEnums tiposReportes;

    @Column(name = "fecha_generacion")
    private Date fechaGeneracion;

    @Column
    private String periodo;

    @ManyToOne
    @JoinColumn(name = "id_administrador")
    private AdminEntity admin;

    @OneToMany(mappedBy = "reportes")
    private List<DetalleReportesEntity> detalleReportes;
}
