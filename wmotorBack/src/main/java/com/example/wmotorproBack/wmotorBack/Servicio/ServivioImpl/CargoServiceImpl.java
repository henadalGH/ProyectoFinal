package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;
import com.example.wmotorproBack.wmotorBack.Repository.CargoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.CargoService;

@Service
public class CargoServiceImpl implements CargoService{
    
    @Autowired
    private CargoRepository cargoRepository;


    public List<CargosEntity>  obtenerCargos(){
        return cargoRepository.findAll();
    }


    @Override
    public CargosEntity obtenerCargoPorId(Long id) {
        return cargoRepository.getReferenceById(id);
        
    }
}
