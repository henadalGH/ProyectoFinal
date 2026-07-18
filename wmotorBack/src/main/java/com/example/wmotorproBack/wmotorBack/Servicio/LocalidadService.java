package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerLocalidadDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.LocalidadEntity;

public interface LocalidadService {

    ObtenerLocalidadDTO tomapLocalidad(LocalidadEntity localidadEntity);
    List<ObtenerLocalidadDTO> obtenerLocalidade();

}
