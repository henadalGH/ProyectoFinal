package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.EmpleadoService;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<EmpleadoEntity> getAllEmpleado() {
        return empleadoRepository.findAll();
    }
    
}
