package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoStockBajoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.StockRepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RepuestosEntity;

public interface RepuestoService {

    List<RepuestoDTO> obtenerRepuesto();
    RepuestoDTO toMapRepuesto(RepuestosEntity repuesto);
    RepuestoDTO obtenerRepuestoPorId(Long id);
    ResponceDTO aumentarStok(Long idRepuesto, StockRepuestoDTO stockRepuestoDTO);
    ResponceDTO disminuirStock(Long idRepuesto, StockRepuestoDTO stockRepuestoDTO);
    ResponceDTO activaDesactivaRepuesto(Long idRepuesto, Boolean activo);
    List<RepuestoStockBajoDTO> obtenerRepuestoBajoStock();
}
