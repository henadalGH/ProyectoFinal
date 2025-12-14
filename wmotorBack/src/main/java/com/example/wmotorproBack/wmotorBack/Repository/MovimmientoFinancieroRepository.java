package com.example.wmotorproBack.wmotorBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;

@Repository
public interface MovimmientoFinancieroRepository extends JpaRepository<MovimientoFinancieroEntity, Long> {

}
