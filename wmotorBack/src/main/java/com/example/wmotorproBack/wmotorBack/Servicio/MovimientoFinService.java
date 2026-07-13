package com.example.wmotorproBack.wmotorBack.Servicio;

import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.BalanceMensualDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.UltimosMovimientosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;

public interface MovimientoFinService {

    // CRUD
    List<MovimientoFinancieroEntity> getAllMovimiento();

    ResponceDTO crearMovimiento(MovimientoDTO movimiento) throws Exception;

    // Filtros
    List<MovimientoFinancieroEntity> obtenerPorFecha(LocalDate fecha);

    List<MovimientoFinancieroEntity> obtenerEntreFechas(LocalDate fechaInicio, LocalDate fechaFin);

    List<MovimientoFinancieroEntity> obtenerPorMes(int mes, int anio);

    List<MovimientoFinancieroEntity> obtenerPorCategoria(Long idCategoria);

    List<MovimientoFinancieroEntity> obtenerPorTipo(String tipoMovimiento);

    // Dashboard
    Double totalIngresos( LocalDate desde, LocalDate hasta);

    Double totalEgresos(LocalDate desde, LocalDate hasta);

    Double balanceGeneral(LocalDate desde,  LocalDate hasta);

    List<BalanceMensualDTO> obtenerBalanceMensual(int anio);

    List<UltimosMovimientosDTO> ultimosMovimientos();
}