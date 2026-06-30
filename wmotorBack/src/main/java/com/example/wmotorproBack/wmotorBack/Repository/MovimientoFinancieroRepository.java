package com.example.wmotorproBack.wmotorBack.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;

import java.time.LocalDate;

public interface MovimientoFinancieroRepository extends JpaRepository<MovimientoFinancieroEntity, Long> {

    List<MovimientoFinancieroEntity> findByFechaRegistro(LocalDate fecha);

List<MovimientoFinancieroEntity> findByFechaRegistroBetween(
        LocalDate inicio,
        LocalDate fin);

List<MovimientoFinancieroEntity> findByCategoriaId(
        Long idCategoria);

List<MovimientoFinancieroEntity> findByCategoriaMovimientos(
        MovimientosEnum movimiento);

        List<MovimientoFinancieroEntity> findByTipoMovimiento(
        MovimientosEnum tipoMovimiento);

        List<MovimientoFinancieroEntity> findTop10ByOrderByFechaRegistroDesc();

        
}
