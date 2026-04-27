package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoTurnosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;

@Repository
public interface EstadoTurnoRepository extends JpaRepository<EstadoTurnosEntity, Long> {

    Optional<EstadoTurnosEntity> findByEstadoTurno(EstadoTurnoEnums estado);
    

}
