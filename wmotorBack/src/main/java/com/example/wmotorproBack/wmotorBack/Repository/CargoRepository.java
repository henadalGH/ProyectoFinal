package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum; 


public interface CargoRepository extends JpaRepository<CargosEntity, Long>{

    boolean existsByCargo(CargosEnum cargo);
    Optional<CargosEntity> findByCargo(CargosEnum cargo);
}
