package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;
import com.example.wmotorproBack.wmotorBack.Repository.MovimientoFinancieroRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.MovimientoFinService;

@Service
public class MovimientoFinServiceImpl implements MovimientoFinService {

    @Autowired
    private MovimientoFinancieroRepository movimientoFinancieroRepository;

    @Override
    public List<MovimientoFinancieroEntity> getAllMovimiento() {
        
        return movimientoFinancieroRepository.findAll();
    }

  
    @Override
    public ResponceDTO crearMovimiento(MovimientoDTO movimiento, MovimientosEnum movimientosEnum) throws Exception {
        

        try {

            ResponceDTO responce = new ResponceDTO();

            MovimientoFinancieroEntity movimientos = new MovimientoFinancieroEntity();
            movimientos.setTipo_movimiento(movimientosEnum);
            movimientos.setCategoria(movimiento.getCategoria());
            movimientos.setConcepto(movimiento.getConcepto());
            movimientos.setImporte(movimiento.getImporte());
            movimientos.setFechaRegistro(new Date());
            
            movimientoFinancieroRepository.save(movimientos);

            responce.setMensage("Ya se registro el: " + movimientos.getTipo_movimiento());

            return responce;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
        
    }


}
