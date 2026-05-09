package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PrioridadEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;

@Repository
public interface PrioridadRepository extends JpaRepository<PrioridadEntity, Long> {
    
    Optional<PrioridadEntity> findByPrioridad(PrioridadEnum prioridad);


}
