package com.example.wmotorproBack.wmotorBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MotivoCancelacionEntity;

@Repository
public interface MotivoCancelacionRepository extends JpaRepository<MotivoCancelacionEntity, Long> {

}
