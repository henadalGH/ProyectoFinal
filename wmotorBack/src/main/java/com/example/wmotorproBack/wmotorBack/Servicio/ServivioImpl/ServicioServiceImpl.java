package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ServiciosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ServicioEntity;
import com.example.wmotorproBack.wmotorBack.Repository.ServicioRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.ServicioService;


@Service
public class ServicioServiceImpl implements ServicioService {


    @Autowired
    private ServicioRepository servicioRepository;



    @Override
    public List<ServicioEntity> ontenerTodosLosServicio() {
        return servicioRepository.findAll();
    }

    @Override
    public ServiciosDTO ontenerSercicioPorId(Long id) {
        
        ServicioEntity servicio = servicioRepository.getReferenceById(id);

        ServiciosDTO dto = new ServiciosDTO();

        dto.setId(servicio.getId());
        dto.setNombre(servicio.getNombre());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setDuracion(servicio.getDuracionEstimada());
        dto.setPrecioBase(servicio.getPrecion_base());



        return dto;
    }

}
