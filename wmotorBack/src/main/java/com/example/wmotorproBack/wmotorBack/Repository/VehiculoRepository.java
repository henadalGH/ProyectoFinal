package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long>{

    boolean existsByPatente(String patente);
    
    List<VehiculoEntity> findByClienteId(Long idCliente);
}
