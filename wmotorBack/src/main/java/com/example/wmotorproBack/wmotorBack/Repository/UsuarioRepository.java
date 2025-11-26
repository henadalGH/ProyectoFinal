package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    
    Optional<UsuarioEntity> findByEmail(String email);

    
    boolean existsByEmail(String email);
}
