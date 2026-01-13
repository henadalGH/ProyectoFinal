package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientosFinDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Repository.MovimmientoFinancieroRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.MovimientoFinancieroService;

@Service
public class MovimientoFinancieroServiceImpl implements MovimientoFinancieroService {

    
    private MovimmientoFinancieroRepository movimientoRepository;
    
    @Override
    public ResponceDTO registrarFinasas(MovimientosFinDTO movimiento) throws Exception {
    
        try {
            
            ResponceDTO responceDTO = new ResponceDTO();

            if (responceDTO.getNumOfErrors() < 0) {

                return responceDTO;
            }

            MovimientoFinancieroEntity movimientos = new MovimientoFinancieroEntity();
            movimientos.setTipo_movimiento(movimiento.getTipoMovimiento());
            movimientos.setCategoria(movimiento.getCategoria());
            movimientos.setConcepto(movimiento.getConcepto());
            movimientos.setImporte(movimiento.getImporte());
            movimientos.setFechaRegistro(movimiento.getFechaRegistro());
            
            movimientoRepository.save(movimientos);

            responceDTO.setMensage("Movimiento registrada");

            return responceDTO;
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
        
    }



}
