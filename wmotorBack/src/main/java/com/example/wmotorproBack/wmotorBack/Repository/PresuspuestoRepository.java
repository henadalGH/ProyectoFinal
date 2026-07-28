package com.example.wmotorproBack.wmotorBack.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoPresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;

public interface PresuspuestoRepository extends JpaRepository<PresupuestoEntity, Long> {

    List<PresupuestoEntity> findByVehiculoId(Long id);
    List<PresupuestoEntity> findByEstadoPresupuesto(EstadoPresupuestoEntity estado);
    List<PresupuestoEntity> findTop5ByOrderByFechaRegistroDesc();
    List<PresupuestoEntity> findTop5ByVehiculoClienteIdAndEstadoPresupuestoEstadoPresupuestoInOrderByFechaRegistroDesc(
            Long idCliente, List<EstadoPresupuestoEnum> estados);
    List<PresupuestoEntity> findByVehiculoClienteId(Long id);
    List<PresupuestoEntity> findByVehiculoClienteIdAndEstadoPresupuesto(
            Long idCliente, EstadoPresupuestoEntity estado);
    Long countByVehiculoClienteIdAndEstadoPresupuesto(
            Long idCliente, EstadoPresupuestoEntity estado);

    // Total facturado
    @Query("""
           SELECT COALESCE(SUM(p.total),0)
           FROM PresupuestoEntity p
           """)
    Double totalFacturado();


    // Total cobrado
    @Query("""
           SELECT COALESCE(SUM(p.total),0)
           FROM PresupuestoEntity p
           WHERE p.estadoPresupuesto.estadoPresupuesto = 'PAGADO'
           """)
    Double totalCobrado();


    // Total pendiente
    @Query("""
           SELECT COALESCE(SUM(p.total),0)
           FROM PresupuestoEntity p
           WHERE p.estadoPresupuesto.estadoPresupuesto = 'ENVIADO'
           """)
    Double totalPendiente();

    @Query("""
           SELECT COUNT(p)
           FROM PresupuestoEntity p
           WHERE p.estadoPresupuesto.estadoPresupuesto = 'PAGADO'
           """)
    Long contarFacturasCobradas();

    @Query("""
           SELECT COUNT(p)
           FROM PresupuestoEntity p
           WHERE p.estadoPresupuesto.estadoPresupuesto = 'ENVIADO'
           """)
    Long contarFacturasPendientes();

}