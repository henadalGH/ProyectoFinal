package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoOrdenEntity;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajoEntity, Long>{

    List<OrdenTrabajoEntity> findByVehiculo(VehiculoEntity vehiculo);

    List<OrdenTrabajoEntity> findByEstadoOrden(EstadoOrdenEntity estadoOrden);

}
