package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RolesEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

    Optional<RolesEntity> findByNombre(RolesEnum rol);
}
