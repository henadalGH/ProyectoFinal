package com.example.wmotorproBack.wmotorBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;



@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long>{

    boolean existsByPatente(String patente);
}
