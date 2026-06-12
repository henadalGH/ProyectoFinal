package com.example.wmotorproBack.wmotorBack.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RolesEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

     boolean existsByNombre(RolesEnum nombre);

    Optional<RolesEntity> findByNombre(RolesEnum nombre);
}
