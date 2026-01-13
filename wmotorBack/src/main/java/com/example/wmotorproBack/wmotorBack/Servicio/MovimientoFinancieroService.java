package com.example.wmotorproBack.wmotorBack.Servicio;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientosFinDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;

public interface MovimientoFinancieroService {

    ResponceDTO registrarFinasas(MovimientosFinDTO movimiento) throws Exception;

}
