package com.example.wmotorproBack.wmotorBack.Modelo.Entity;


import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.IndicadorEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TendenciasEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TiposEstadisticasEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.UnidadMedidasEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "estadisticas", schema = "wmotorpro")
public class EstadisticasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadisticas")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estadisticas")
    private TiposEstadisticasEnum tipoEstadisticas;

    @Enumerated(EnumType.STRING)
    @Column(name = "indicador")
    private IndicadorEnum indicador;

    @Column(name = "valor")
    private Integer valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida")
    private UnidadMedidasEnum medida;

    @Enumerated(EnumType.STRING)
    @Column(name = "tendencia")
    private TendenciasEnum tendencia;

    @Column(name = "periodo")
    private LocalDate periodo;

    @Column(name = "fuentes_Datos")
    private String fuente;

    @OneToMany(mappedBy = "estadisticas")
    private List<DetalleReportesEntity> detallesReportes;


}

