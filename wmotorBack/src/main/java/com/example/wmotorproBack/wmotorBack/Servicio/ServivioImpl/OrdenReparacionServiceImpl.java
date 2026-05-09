package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.DetalleOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.OrdenReparacionDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetalleOrdenEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoOrdenEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PrioridadEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;
import com.example.wmotorproBack.wmotorBack.Repository.DetalleOrdenReposistory;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoOrdenRepository;
import com.example.wmotorproBack.wmotorBack.Repository.OrdenTrabajoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.PresuspuestoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.PrioridadRepository;
import com.example.wmotorproBack.wmotorBack.Repository.VehiculoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.NumeracionService;
import com.example.wmotorproBack.wmotorBack.Servicio.OrdenReparacionService;

@Service
public class OrdenReparacionServiceImpl implements OrdenReparacionService{


    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;

    @Autowired 
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PrioridadRepository prioridadRepository;

    @Autowired
    private PresuspuestoRepository presuspuestoRepository;

    @Autowired
    private NumeracionService numeracionService;

    @Autowired
    private DetalleOrdenReposistory detalleOrdenReposistory;


    @Override
    public ResponceDTO crearReparacion(OrdenReparacionDTO orden) {

        ResponceDTO response = new ResponceDTO();

        OrdenTrabajoEntity ordenTrabajoEntity = new OrdenTrabajoEntity();
        ordenTrabajoEntity.setFechaEminsion(LocalDate.now());
        ordenTrabajoEntity.setMotivoCancelacion(orden.getMotivoCancelacion());
        ordenTrabajoEntity.setFechaFin(orden.getFechaCierre());
        ordenTrabajoEntity.setFechaEntragaCliente(orden.getFechaEntragaCliente());


        //Estado de la orden 
        EstadoOrdenEntity estadoOrden = estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.ASIGNADA)
        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        ordenTrabajoEntity.setEstadoOrden(estadoOrden);

        //Seteando el empleado
        EmpleadoEntity empleado = empleadoRepository.findById(orden.getIdEmpleado())
        .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        ordenTrabajoEntity.setEmpleado(empleado);


        //Relacion con el vehiculo a reparar
        VehiculoEntity vehiculo = vehiculoRepository.findById(orden.getIdVehiculo())
        .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));
        ordenTrabajoEntity.setVehiculo(vehiculo);

        //Prioridar de la orden
        PrioridadEntity prioridad = prioridadRepository.findByPrioridad(PrioridadEnum.MEDIA)
        .orElseThrow(() -> new RuntimeException("Prioridad no encontrada"));
        ordenTrabajoEntity.setPrioridad(prioridad);

        //presupuesto relacionado
        PresupuestoEntity presupuesto = presuspuestoRepository.findById(orden.getId())
        .orElseThrow(() -> new RuntimeException("id de presupusto no encontrado"));
        ordenTrabajoEntity.setPresupuesto(presupuesto);

        //Detalle de la orden

        Long numeroOrden = numeracionService.generarNumeroOrden();
        ordenTrabajoEntity.setNuemeroOrden(numeroOrden);

        List<DetalleOrdenEntity> detalles = new ArrayList<>();

        for(DetalleOrdenDTO detalle : orden.getDetalleOrden()){
            
            DetalleOrdenEntity detalleOrdenEntity = new DetalleOrdenEntity();
            detalleOrdenEntity.setTrabajoRealizados(detalle.getTrabajoRealizado());
            detalleOrdenEntity.setCantidad(detalle.getCantidad());
            detalleOrdenEntity.setCodigo(detalle.getCodigo());
            detalleOrdenEntity.setTipoItem(detalle.getTipoItem());
            detalleOrdenEntity.setObservaciones(detalle.getObservaciones());

            detalleOrdenEntity.setOrden(ordenTrabajoEntity);

            detalles.add(detalleOrdenEntity);
            detalleOrdenReposistory.save(detalleOrdenEntity);
        }

         ordenTrabajoEntity.setDetalleOrden(detalles);

        ordenTrabajoRepository.save(ordenTrabajoEntity);

        response.setMensage("La orden se a creado con exito");
        return response;
    }


    @Override
    public List<ObtenerOrdenDTO> obtenerTodaLasOrdenes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTodaLasOrdenes'");
    }

    @Override
    public ObtenerOrdenDTO obtnerOrdenPorId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtnerOrdenPorId'");
    }

    @Override
    public ResponceDTO actualizarEstaOrden(EstadoOrdenEnums estado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarEstaOrden'");
    }

    @Override
    public ObtenerOrdenDTO obtenerPorIdVehiculo(Long idVehiculo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPorIdVehiculo'");
    }

    @Override
    public List<ObtenerOrdenDTO> obtenerOrdenPorEstado(EstadoOrdenEnums estado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerOrdenPorEstado'");
    }

    @Override
    public ObtenerOrdenDTO toMapObtenerOrdenDTO(OrdenTrabajoEntity ordenTrabajo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toMapObtenerOrdenDTO'");
    }

}
