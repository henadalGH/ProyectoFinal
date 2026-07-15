package com.example.wmotorproBack.wmotorBack.Repository;

import java.time.LocalDate; 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ServicioMasSolicitadosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosPorEstadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosPorMesDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoTurnosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;


public interface TurnoRepository extends JpaRepository<TurnoEntity, Long>{

    List<TurnoEntity> findByVehiculo(VehiculoEntity vehiculo);

    List<TurnoEntity> findByEstado(EstadoTurnosEntity estado);

    List<TurnoEntity> findByVehiculo_IdAndEstado(Long idVehiculo, EstadoTurnosEntity estado);

    List<TurnoEntity> findByVehiculoClienteIdAndEstado(Long idCliente, EstadoTurnosEntity estado);

    List<TurnoEntity> findByFechaHora(LocalDate fecha);
    List<TurnoEntity> findByFechaHoraGreaterThanEqual(LocalDate fecha);

    List<TurnoEntity> findByVehiculoClienteIdAndFechaHoraGreaterThanEqual(Long id, LocalDate fecha);
    Long countByEstado(EstadoTurnosEntity estado);

        @Query("""
    SELECT new com.example.wmotorproBack.wmotorBack.Modelo.DTO.ServicioMasSolicitadosDTO(
        s.nombre,
        COUNT(s)
    )
    FROM TurnoEntity t
    JOIN t.servicio s
    GROUP BY s.nombre
    """)
    List<ServicioMasSolicitadosDTO> servicioMasSolicitado();

    @Query("""
    SELECT new com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosPorMesDTO(
        MONTH(t.fechaHora), COUNT(t)
    )
    FROM TurnoEntity t
    GROUP BY MONTH(t.fechaHora)
    ORDER BY MONTH(t.fechaHora)
    """)
    List<TurnosPorMesDTO> contarTurnosPorMes();

    @Query("""
    SELECT new com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosPorEstadoDTO(
        t.estado.estadoTurno,
        COUNT(t)
    )
    FROM TurnoEntity t
    GROUP BY t.estado.estadoTurno
""")
List<TurnosPorEstadoDTO> contarTurnosPorEstado();

} 
 