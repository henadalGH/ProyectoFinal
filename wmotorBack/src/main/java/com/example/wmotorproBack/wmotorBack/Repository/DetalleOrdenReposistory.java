package com.example.wmotorproBack.wmotorBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetalleOrdenEntity;
import java.util.List;

public interface DetalleOrdenReposistory extends JpaRepository<DetalleOrdenEntity, Long> {

    List<DetalleOrdenEntity> findByOrdenId(Long idOrden);

}
