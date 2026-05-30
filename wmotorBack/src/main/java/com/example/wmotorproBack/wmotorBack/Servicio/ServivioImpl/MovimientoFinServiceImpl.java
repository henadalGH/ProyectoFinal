package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;
import com.example.wmotorproBack.wmotorBack.Repository.MovimientoFinancieroRepository;
import com.example.wmotorproBack.wmotorBack.Repository.CategoriaMovimientoRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CategoriaMovimientoEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.MovimientoFinService;

@Service
public class MovimientoFinServiceImpl implements MovimientoFinService {

    @Autowired
    private MovimientoFinancieroRepository movimientoFinancieroRepository;

    @Autowired
    private CategoriaMovimientoRepository categoriaMovimientoRepository;

    @Override
    public List<MovimientoFinancieroEntity> getAllMovimiento() {
        
        return movimientoFinancieroRepository.findAll();
    }


    @Override
    public ResponceDTO crearMovimiento(MovimientoDTO movimiento, MovimientosEnum movimientosEnum) throws Exception {
        

        try {

            ResponceDTO responce = new ResponceDTO();

            // Validar que la categoría exista y coincida con el tipo de movimiento
            java.util.Optional<CategoriaMovimientoEntity> catOpt = categoriaMovimientoRepository.findByCategoria(movimiento.getCategoria());
            if (catOpt.isEmpty()) {
                throw new Exception("Categoría no encontrada: " + movimiento.getCategoria());
            }

            CategoriaMovimientoEntity categoria = catOpt.get();
            if (categoria.getMovimientos() != movimientosEnum) {
                throw new Exception("La categoría seleccionada no coincide con el tipo de movimiento: " + movimientosEnum);
            }

            MovimientoFinancieroEntity movimientos = new MovimientoFinancieroEntity();
            movimientos.setTipo_movimiento(movimientosEnum);
            movimientos.setConcepto(movimiento.getConcepto());
            movimientos.setImporte(movimiento.getImporte());
            movimientos.setFechaRegistro(LocalDate.now());
            movimientos.setCategoria(categoria);

            movimientoFinancieroRepository.save(movimientos);

            responce.setMensage("Ya se registro el: " + movimientos.getTipo_movimiento());

            return responce;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
        
    }


    @Override
    public List<MovimientoFinancieroEntity> obtenerPorFecha(LocalDate fechDate) {
        return movimientoFinancieroRepository.findByFechaRegistro(fechDate);
    }


    @Override
    public List<MovimientoFinancieroEntity> obterEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return movimientoFinancieroRepository.findByFechaRegistroBetween(fechaInicio, fechaFin);
    }


    @Override
    public List<MovimientoFinancieroEntity> obtenrPorMes(int mes, int anio) {
        
        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());

        return movimientoFinancieroRepository.findByFechaRegistroBetween(inicio, fin);
        
    }


}
