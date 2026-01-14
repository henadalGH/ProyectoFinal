package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RepuestosEntity;
import com.example.wmotorproBack.wmotorBack.Repository.RepuestoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.RepuestoService;

@Service
public class RepuestoServiceImpl implements RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Override
    public List<RepuestosEntity> getAllRepuesto() {
        
        return repuestoRepository.findAll();
    }

    @Override
    public RepuestoDTO getRepuestoPorId(Long id) {
       
        RepuestosEntity repuesto = repuestoRepository.getReferenceById(id);

        RepuestoDTO repuestoDTO = new RepuestoDTO();
        repuestoDTO.setId(repuesto.getId());
        repuestoDTO.setNombre(repuesto.getNombre());
        repuestoDTO.setMarca(repuesto.getMarca());
        repuestoDTO.setCodigo(repuesto.getCodigo());


        return repuestoDTO;
    }

}
