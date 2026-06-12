package com.example.wmotorproBack.wmotorBack.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import java.time.LocalDate;

public interface MovimientoFinancieroRepository extends JpaRepository<MovimientoFinancieroEntity, Long> {

    List<MovimientoFinancieroEntity> findByFechaRegistro(LocalDate fechaRegistro);
    List<MovimientoFinancieroEntity> findByFechaRegistroBetween(LocalDate fechaRegistroInicio, LocalDate fechaRegistroFin);
}
