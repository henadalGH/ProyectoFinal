package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;

@Repository
public interface TurnoRepository extends JpaRepository<TurnoEntity, Long>{

    List<TurnoEntity> findByVehiculo(VehiculoEntity vehiculo);

}
