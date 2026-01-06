package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ClienteDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ClienteEntity;

public interface ClienteService {

    public List<ClienteEntity> obtenerClientes();
    public ClienteDTO obtenerClientePorId(Long id);

}
