package com.example.wmotorproBack.wmotorBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CategoriaMovimientoEntity;

@Repository
public interface CategoriaMovimientoRepository extends JpaRepository<CategoriaMovimientoEntity, Long>{

	java.util.Optional<CategoriaMovimientoEntity> findByCategoria(String categoria);

}
