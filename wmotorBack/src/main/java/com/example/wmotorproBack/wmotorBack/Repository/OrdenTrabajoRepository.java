package com.example.wmotorproBack.wmotorBack.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoOrdenEntity;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajoEntity, Long>{


    List<OrdenTrabajoEntity> findByEstadoOrden(EstadoOrdenEntity estadoOrden);
    List<OrdenTrabajoEntity> findByEmpleadoAndTurnoFechaHora(EmpleadoEntity empleado, LocalDate fecha);
    EstadoOrdenEntity findByEstadoOrden(EstadoOrdenEnums estado);

}
