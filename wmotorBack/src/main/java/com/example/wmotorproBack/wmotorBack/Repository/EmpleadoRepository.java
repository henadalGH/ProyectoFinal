package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {


    List<EmpleadoEntity> findByCargo(CargosEntity cargo);    
}


