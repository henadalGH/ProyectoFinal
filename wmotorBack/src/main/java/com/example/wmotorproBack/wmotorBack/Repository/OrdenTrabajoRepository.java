package com.example.wmotorproBack.wmotorBack.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoOrdenEntity;

public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajoEntity, Long>{


    List<OrdenTrabajoEntity> findByEstadoOrden(EstadoOrdenEntity estadoOrden);
    List<OrdenTrabajoEntity> findByEmpleadoIdAndFechaEminsionGreaterThanEqual(Long idEmpleado, LocalDate fecha);
    List<OrdenTrabajoEntity> findByEmpleadoIdAndEstadoOrden(Long idEmpeado, EstadoOrdenEntity estado);
    List<OrdenTrabajoEntity> findTop15ByEmpleadoIdOrderByFechaEminsionDesc(Long idEmpleado);
    Long countByEstadoOrden(EstadoOrdenEntity estado);

    @Query("""
        SELECT o
        FROM OrdenTrabajoEntity o
        WHERE o.empleado = :empleado
        AND FUNCTION('DATE', o.turno.fechaHora) = :fecha
        """)
        List<OrdenTrabajoEntity> findByEmpleadoAndTurnoFechaHora(
            @Param("empleado") EmpleadoEntity empleado,
            @Param("fecha") LocalDate fecha
        );
    

}
