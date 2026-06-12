package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoTurnosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;

public interface TurnoRepository extends JpaRepository<TurnoEntity, Long>{

    List<TurnoEntity> findByVehiculo(VehiculoEntity vehiculo);

    List<TurnoEntity> findByEstado(EstadoTurnosEntity estado);

    List<TurnoEntity> findByVehiculo_IdAndEstado(Long idVehiculo, EstadoTurnosEntity estado);

    List<TurnoEntity> findByVehiculoClienteIdAndEstado(Long idCliente, EstadoTurnosEntity estado);

    

} 
 