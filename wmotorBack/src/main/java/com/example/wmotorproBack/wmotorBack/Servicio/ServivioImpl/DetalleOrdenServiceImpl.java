package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.DetalleOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetalleOrdenEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Repository.DetalleOrdenReposistory;
import com.example.wmotorproBack.wmotorBack.Repository.OrdenTrabajoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.DetalleOrdenService;

@Service
public class DetalleOrdenServiceImpl implements DetalleOrdenService{

    @Autowired
    private DetalleOrdenReposistory detalleOrdenReposistory;

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;
    
    @Override
    public DetalleOrdenDTO toMapDetalleDTO(DetalleOrdenEntity detalleOrden) {

        DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
        detalleOrdenDTO.setCantidad(detalleOrden.getCantidad());
        detalleOrdenDTO.setCodigo(detalleOrden.getCodigo());
        detalleOrdenDTO.setTipoItem(detalleOrden.getTipoItem());
        detalleOrdenDTO.setTrabajoRealizado(detalleOrden.getTrabajoRealizados());

        return detalleOrdenDTO;
       
    }

    @Override
    public ResponceDTO agregarDetalles(List<DetalleOrdenDTO> detalleOrden, Long idOrden) {
        
        ResponceDTO responceDTO = new ResponceDTO();

        OrdenTrabajoEntity ordenEntity = ordenTrabajoRepository.findById(idOrden)
        .orElseThrow(() -> new RuntimeException("Id orden trabajo no encontrada"));

        List<DetalleOrdenEntity> detalles = new ArrayList<>();

        
        for(DetalleOrdenDTO detalle: detalleOrden){


            DetalleOrdenEntity detalleOrdenEntity = new DetalleOrdenEntity();
            detalleOrdenEntity.setCantidad(detalle.getCantidad());
            detalleOrdenEntity.setTrabajoRealizados(detalle.getTrabajoRealizado());
            detalleOrdenEntity.setCodigo(detalle.getCodigo());
            detalleOrdenEntity.setTipoItem(detalle.getTipoItem());
            detalleOrdenEntity.setOrden(ordenEntity);

            detalles.add(detalleOrdenEntity);
            detalleOrdenReposistory.save(detalleOrdenEntity);

        }

        responceDTO.setMensage("Detalles agregados con exito");

        return responceDTO;
    }

    @Override
    public List<DetalleOrdenDTO> obtenerDetallePorIdOrden(Long idOrden) {
    
        return detalleOrdenReposistory.findByOrdenId(idOrden)
        .stream()
        .map(this::toMapDetalleDTO)
        .collect(Collectors.toList());
    }

    

}
