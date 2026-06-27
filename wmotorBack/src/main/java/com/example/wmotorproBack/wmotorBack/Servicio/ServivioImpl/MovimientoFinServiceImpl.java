package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CategoriaMovimientoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;
import com.example.wmotorproBack.wmotorBack.Repository.CategoriaMovimientoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.MovimientoFinancieroRepository;
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
    public ResponceDTO crearMovimiento(MovimientoDTO movimiento)
            throws Exception {

        CategoriaMovimientoEntity categoria =
                categoriaMovimientoRepository
                        .findByCategoria(movimiento.getCategoria())
                        .orElseThrow(() ->
                                new Exception(
                                        "Categoría no encontrada: "
                                                + movimiento.getCategoria()));

        MovimientoFinancieroEntity entity =
                new MovimientoFinancieroEntity();

        entity.setConcepto(movimiento.getConcepto());
        entity.setImporte(movimiento.getImporte());
        entity.setFechaRegistro(LocalDate.now());
        entity.setCategoria(categoria);

        movimientoFinancieroRepository.save(entity);

        ResponceDTO response = new ResponceDTO();
        response.setMensage(
                "Movimiento registrado correctamente");

        return response;
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerPorFecha(
            LocalDate fecha) {

        return movimientoFinancieroRepository
                .findByFechaRegistro(fecha);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerEntreFechas(
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        return movimientoFinancieroRepository
                .findByFechaRegistroBetween(
                        fechaInicio,
                        fechaFin);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerPorMes(
            int mes,
            int anio) {

        LocalDate inicio =
                LocalDate.of(anio, mes, 1);

        LocalDate fin =
                inicio.withDayOfMonth(
                        inicio.lengthOfMonth());

        return movimientoFinancieroRepository
                .findByFechaRegistroBetween(
                        inicio,
                        fin);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerPorCategoria(
            Long idCategoria) {

        return movimientoFinancieroRepository
                .findByCategoriaId(idCategoria);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerPorTipo(
            String tipoMovimiento) {

        MovimientosEnum tipo =
                MovimientosEnum
                        .valueOf(
                                tipoMovimiento
                                        .toUpperCase());

        return movimientoFinancieroRepository
                .findByCategoriaMovimientos(tipo);
    }

    @Override
    public Double totalIngresos(
            LocalDate desde,
            LocalDate hasta) {

        return movimientoFinancieroRepository
                .findByFechaRegistroBetween(
                        desde,
                        hasta)
                .stream()
                .filter(m ->
                        m.getCategoria()
                                .getMovimientos()
                                == MovimientosEnum.INGRESO)
                .mapToDouble(
                        MovimientoFinancieroEntity::getImporte)
                .sum();
    }

    @Override
    public Double totalEgresos(
            LocalDate desde,
            LocalDate hasta) {

        return movimientoFinancieroRepository
                .findByFechaRegistroBetween(
                        desde,
                        hasta)
                .stream()
                .filter(m ->
                        m.getCategoria()
                                .getMovimientos()
                                == MovimientosEnum.GASTOS)
                .mapToDouble(
                        MovimientoFinancieroEntity::getImporte)
                .sum();
    }

    @Override
    public Double balanceGeneral(
            LocalDate desde,
            LocalDate hasta) {

        return totalIngresos(desde, hasta)
                - totalEgresos(desde, hasta);
    }
}