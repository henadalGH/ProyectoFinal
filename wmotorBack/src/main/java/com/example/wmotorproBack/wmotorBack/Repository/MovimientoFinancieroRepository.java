package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;

@Repository
public interface MovimientoFinancieroRepository extends JpaRepository<MovimientoFinancieroEntity, Long> {
    List<MovimientoFinancieroEntity> findByFechaRegistroBetween(Date fechaInicio, Date fechaFin);
}
