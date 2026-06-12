package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoTurnosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;
public interface EstadoTurnoRepository extends JpaRepository<EstadoTurnosEntity, Long> {

    Optional<EstadoTurnosEntity> findByEstadoTurno(EstadoTurnoEnums estado);


}
 