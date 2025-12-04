package com.example.wmotorproBack.wmotorBack.Modelo.Entity;


import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.IndicadorEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TendenciasEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TiposEstadisticasEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.UnidadMedidasEnum;

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
@Table(name = "estadisticas", schema = "wmotorpro")
public class EstadisticasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadisticas")
    private Long id;

    @Column(name = "tipo_estadisticas")
    private TiposEstadisticasEnum tipoEstadisticas;

    @Column(name = "indicador")
    private IndicadorEnum indicador;

    @Column(name = "valor")
    private Integer valor;

    @Column(name = "unidad_medida")
    private UnidadMedidasEnum medida;

    @Column(name = "tendencia")
    private TendenciasEnum tendencia;

    @Column(name = "periodo")
    private LocalDate periodo;

    @Column(name = "fuentes_Datos")
    private String fuente;

    @ManyToOne
    @JoinColumn(name = "id_adminis")
    private AdminEntity admin;


}

