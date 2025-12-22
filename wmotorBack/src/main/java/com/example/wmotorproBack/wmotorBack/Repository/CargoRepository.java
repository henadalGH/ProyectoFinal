package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;


@Repository
public interface CargoRepository extends JpaRepository<CargosEntity, Long>{

    boolean existsByCargo(CargosEnum cargo);

    Optional<CargosEntity> findByCargo(CargosEnum nombre);
}
