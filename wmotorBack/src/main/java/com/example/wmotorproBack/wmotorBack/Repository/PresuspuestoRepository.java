package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoPresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;

public interface PresuspuestoRepository extends JpaRepository<PresupuestoEntity, Long> {

    List<PresupuestoEntity> findByVehiculoId(Long id);
    Optional<EstadoPresupuestoEntity> findByEstadoPresupuesto(EstadoPresupuestoEnum estado);
    List<PresupuestoEntity> findTop5ByOrderByFechaRegistroDesc();

}
