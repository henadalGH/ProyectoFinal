package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import com.example.wmotorproBack.wmotorBack.Repository.LocalidadRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerLocalidadDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.LocalidadEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.LocalidadService;

@Service
public class LocalidaServiceImpl implements LocalidadService{

    @Autowired
    private LocalidadRepository localidadRepository;

    @Override
    public ObtenerLocalidadDTO tomapLocalidad(LocalidadEntity localidadEntity) {
        
        ObtenerLocalidadDTO localidadDTO = new ObtenerLocalidadDTO();
        localidadDTO.setId(localidadEntity.getId());
        localidadDTO.setNombreLocalidad(localidadEntity.getNombreLocalidad());
        localidadDTO.setNombreProvincia(localidadEntity.getProvincia().getNombreProvincia());

        return localidadDTO;
    }

    @Override
    public List<ObtenerLocalidadDTO> obtenerLocalidade() {
        return localidadRepository.findAll()
        .stream()
        .map(this::tomapLocalidad)
        .toList();
    }

}
