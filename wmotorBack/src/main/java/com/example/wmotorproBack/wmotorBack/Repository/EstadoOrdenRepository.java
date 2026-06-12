package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoOrdenEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrdenEntity, Long>{

    Optional<EstadoOrdenEntity> findByEstadoOrden(EstadoOrdenEnums estado);

}
