package com.example.wmotorproBack.wmotorBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetalleOrdenEntity;

@Repository
public interface DetalleOrdenReposistory extends JpaRepository<DetalleOrdenEntity, Long> {

}
