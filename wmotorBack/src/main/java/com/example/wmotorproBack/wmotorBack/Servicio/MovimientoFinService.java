package com.example.wmotorproBack.wmotorBack.Servicio;

import java.time.LocalDate;
import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;

public interface MovimientoFinService {

    List<MovimientoFinancieroEntity> getAllMovimiento();
    ResponceDTO crearMovimiento(MovimientoDTO movimiento, MovimientosEnum movimientosEnum) throws Exception;
    List<MovimientoFinancieroEntity> obtenerPorFecha(LocalDate fechDate);
    List<MovimientoFinancieroEntity> obterEntreFechas(LocalDate fechaInicio, LocalDate fechaFin);
    List<MovimientoFinancieroEntity> obtenrPorMes(int mes , int anio);
    
}
    

