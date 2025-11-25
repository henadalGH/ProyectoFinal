package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;
import com.example.wmotorproBack.wmotorBack.Repository.UsuarioRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.UsuarioServicio;



@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public List<UsuarioEntity> findAllUsers() {
        return usuarioRepository.findAll();    
    }
    
    
}
