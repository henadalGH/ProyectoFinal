package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerRepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoStockBajoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RepuestosEntity;

public interface RepuestoRepository extends JpaRepository<RepuestosEntity, Long> {

    @Query("""
       SELECT new com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoStockBajoDTO(
            r.id,
            r.nombre,
            r.marca,
            r.codigo,
            r.stock,
            r.stockMin
       )
       FROM RepuestosEntity r
       WHERE r.stock <= r.stockMin
           """)
    List<RepuestoStockBajoDTO> buscarStockBajo();

    @Query("""
       SELECT new com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerRepuestoDTO(
            r.id,
            r.nombre,
            r.stock,
            r.marca,
            r.codigo 
       )
       FROM RepuestosEntity r
       
           """)
    List<ObtenerRepuestoDTO> obtenerRepuesto();

    Optional<RepuestosEntity> findByCodigo(String codigo);

    List<RepuestosEntity> findByMarcaIgnoreCase(String marca);

    List<RepuestosEntity> findByMarcaIgnoreCaseAndNombreIgnoreCase(
            String marca,
            String nombre
    );

    List<RepuestosEntity> findByNombreIgnoreCase(String nombre);

}