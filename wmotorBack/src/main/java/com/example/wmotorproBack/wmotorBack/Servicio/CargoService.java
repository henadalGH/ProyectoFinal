package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;

public interface CargoService {

    public List<CargosEntity> obtenerCargos();
    public CargosEntity obtenerCargoPorId(Long id);
}
