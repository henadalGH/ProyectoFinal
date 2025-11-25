package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    // Spring Data JPA genera automáticamente:
    // SELECT * FROM usuario WHERE email = ?
    Optional<UsuarioEntity> findByEmail(String email);

    // Opcional: validar si ya existe un email (útil para registro)
    boolean existsByEmail(String email);
}
