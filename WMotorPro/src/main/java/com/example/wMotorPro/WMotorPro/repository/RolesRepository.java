package com.example.wMotorPro.WMotorPro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wMotorPro.WMotorPro.modelo.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer>{

    
} 
