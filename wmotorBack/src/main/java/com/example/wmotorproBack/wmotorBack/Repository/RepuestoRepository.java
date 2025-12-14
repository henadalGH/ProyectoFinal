package com.example.wmotorproBack.wmotorBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RepuestosEntity;

@Repository
public interface RepuestoRepository extends JpaRepository<RepuestosEntity, Long> {
}
