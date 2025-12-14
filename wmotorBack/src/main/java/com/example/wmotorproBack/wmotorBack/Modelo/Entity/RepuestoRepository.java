package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepuestoRepository extends JpaRepository<RepuestosEntity, Long> {
}
