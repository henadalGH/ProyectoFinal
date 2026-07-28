package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ServicioEntity;

public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {

    Optional<ServicioEntity> findByNombreIgnoreCase(String nombre);
    Optional<ServicioEntity> findByNombreContainingIgnoreCase(String nombre);
}
