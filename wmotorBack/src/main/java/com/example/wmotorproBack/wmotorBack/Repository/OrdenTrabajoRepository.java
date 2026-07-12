package com.example.wmotorproBack.wmotorBack.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoOrdenEntity;

public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajoEntity, Long>{


    List<OrdenTrabajoEntity> findByEstadoOrden(EstadoOrdenEntity estadoOrden);
    List<OrdenTrabajoEntity> findByEmpleadoAndTurnoFechaHora(EmpleadoEntity empleado, LocalDate fecha);
    List<OrdenTrabajoEntity> findByEmpleadoIdAndFechaEminsionGreaterThanEqual(Long idEmpleado, LocalDate fecha);
    List<OrdenTrabajoEntity> findTop15ByEmpleadoIdOrderByFechaEminsionDesc(Long idEmpleado);
    
    

}
