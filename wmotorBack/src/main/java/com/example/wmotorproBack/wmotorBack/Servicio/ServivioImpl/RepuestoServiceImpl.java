package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.StockRepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RepuestosEntity;
import com.example.wmotorproBack.wmotorBack.Repository.RepuestoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.RepuestoService;

@Service
public class RepuestoServiceImpl implements RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Override
    public List<RepuestoDTO> obtenerRepuesto() {

        return repuestoRepository.findAll()
        .stream()
            .map(this::toMapRepuesto)
            .collect(Collectors.toList());
    }

    @Override
    public RepuestoDTO toMapRepuesto(RepuestosEntity repuesto) {
        
        RepuestoDTO repuestoDTO = new RepuestoDTO();
        repuestoDTO.setId(repuesto.getId());
        repuestoDTO.setNombre(repuesto.getNombre());
        repuestoDTO.setMarca(repuesto.getMarca());
        repuestoDTO.setCodigo(repuesto.getCodigo());
        repuestoDTO.setStock(repuesto.getStock());
        repuestoDTO.setStockMin(repuesto.getStockMin());
        repuestoDTO.setPrecioCompra(repuesto.getPrecioCompra());
        repuestoDTO.setPrecioVenta(repuesto.getPrecioVenta());
        repuestoDTO.setActivo(repuesto.getActivo());
        repuestoDTO.setCategoria(repuesto.getCategoria());

        return repuestoDTO;
    }

    @Override
    public RepuestoDTO obtenerRepuestoPorId(Long idRepuesto) {

        RepuestosEntity repuestosEntity = repuestoRepository.findById(idRepuesto)
        .orElseThrow(() -> new RuntimeException("Id repuesto no encontrado"));

        return toMapRepuesto(repuestosEntity);        
        
    }

    @Override
    public ResponceDTO aumentarStok(Long idRepuesto, StockRepuestoDTO stockRepuestoDTO) {
        
        ResponceDTO responceDTO = new ResponceDTO();
        
        RepuestosEntity repuestosEntity = repuestoRepository.findById(idRepuesto)
        .orElseThrow(() -> new RuntimeException("Id repuesto no encontrado"));

        
        repuestosEntity.setStock(repuestosEntity.getStock() + stockRepuestoDTO.getCantidad());

        repuestoRepository.save(repuestosEntity);

        responceDTO.setMensage("El stock a aumentado correctamente");
        return responceDTO;
    }

    @Override
    public ResponceDTO disminuirStock(Long idRepuesto, StockRepuestoDTO stockRepuestoDTO) {
        ResponceDTO responceDTO = new ResponceDTO();
        
        RepuestosEntity repuestosEntity = repuestoRepository.findById(idRepuesto)
        .orElseThrow(() -> new RuntimeException("Id repuesto no encontrado"));

        repuestosEntity.setStock(repuestosEntity.getStock() + stockRepuestoDTO.getCantidad());

        repuestoRepository.save(repuestosEntity);

        responceDTO.setMensage("El stock a disminuido correctamente");
        return responceDTO;
    }

    @Override
    public ResponceDTO activaDesactivaRepuesto(Long idRepuesto, Boolean activo) {
        
        ResponceDTO responceDTO = new ResponceDTO();
        
        RepuestosEntity repuestosEntity = repuestoRepository.findById(idRepuesto)
        .orElseThrow(() -> new RuntimeException("Id repuesto no encontrado"));

        if (activo == false) {

            repuestosEntity.setActivo(activo);
            repuestoRepository.save(repuestosEntity);    
            
            responceDTO.setMensage("El repuesto ha sido desactivado con exito");
        } else if (activo == true) {

            repuestosEntity.setActivo(activo);
            repuestoRepository.save(repuestosEntity);    
            
            responceDTO.setMensage("El repuesto ha sido acticado con exito");
            
        }

        return responceDTO;
    }

}
