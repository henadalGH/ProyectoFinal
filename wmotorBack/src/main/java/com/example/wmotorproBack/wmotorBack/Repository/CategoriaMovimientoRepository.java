package com.example.wmotorproBack.wmotorBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CategoriaMovimientoEntity;


public interface CategoriaMovimientoRepository extends JpaRepository<CategoriaMovimientoEntity, Long>{

	java.util.Optional<CategoriaMovimientoEntity> findByCategoria(String categoria);

}
