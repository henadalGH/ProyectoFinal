package com.example.wMotorPro.WMotorPro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wMotorPro.WMotorPro.modelo.Empleado;

@Repository
public interface EmpleadorRepository extends JpaRepository<Empleado, Integer>{

}
