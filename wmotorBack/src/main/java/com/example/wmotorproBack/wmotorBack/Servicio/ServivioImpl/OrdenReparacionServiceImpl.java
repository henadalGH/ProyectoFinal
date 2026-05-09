package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<EstadoOrdenEntity> estadoOpt = estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.ASIGNADA);
        if (estadoOpt.isEmpty()) {
            response.setMensage("Error: Estado ASIGNADA no encontrado");
            return response;
        }
        ordenTrabajoEntity.setEstadoOrden(estadoOpt.get());

        //Seteando el empleado
        Optional<EmpleadoEntity> empleadoOpt = empleadoRepository.findById(orden.getIdEmpleado());
        if (empleadoOpt.isEmpty()) {
            response.setMensage("Error: Empleado no encontrado");
            return response;
        }
        ordenTrabajoEntity.setEmpleado(empleadoOpt.get());

        //Relacion con el vehiculo a reparar
        Optional<VehiculoEntity> vehiculoOpt = vehiculoRepository.findById(orden.getIdVehiculo());
        if (vehiculoOpt.isEmpty()) {
            response.setMensage("Error: Vehículo no encontrado");
            return response;
        }
        ordenTrabajoEntity.setVehiculo(vehiculoOpt.get());

        //Prioridar de la orden
        Optional<PrioridadEntity> prioridadOpt = prioridadRepository.findByPrioridad(PrioridadEnum.MEDIA);
        if (prioridadOpt.isEmpty()) {
            response.setMensage("Error: Prioridad MEDIA no encontrada");
            return response;
        }
        ordenTrabajoEntity.setPrioridad(prioridadOpt.get());

        //presupuesto relacionado
        Optional<PresupuestoEntity> presupuestoOpt = presuspuestoRepository.findById(orden.getId());
        if (presupuestoOpt.isEmpty()) {
            response.setMensage("Error: ID de presupuesto no encontrado");
            return response;
        }
        ordenTrabajoEntity.setPresupuesto(presupuestoOpt.get());

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

        response.setMensage("La orden se ha creado con éxito");
        return response;
    }


    @Override
    public List<ObtenerOrdenDTO> obtenerTodaLasOrdenes() {
        return ordenTrabajoRepository.findAll()
        .stream()
        .map(this:: toMapObtenerOrdenDTO)
        .collect(Collectors.toList());
    }

    @Override
    public ObtenerOrdenDTO obtnerOrdenPorId(Long id) {
        Optional<OrdenTrabajoEntity> ordenOpt = ordenTrabajoRepository.findById(id);
        if (ordenOpt.isEmpty()) {
            return null;
        }
        return toMapObtenerOrdenDTO(ordenOpt.get());
    }

    @Override
    public ResponceDTO actualizarEstaOrden(EstadoOrdenEnums estado, Long id) {
        ResponceDTO responceDTO = new ResponceDTO();

        Optional<EstadoOrdenEntity> estadoOpt = estadoOrdenRepository.findByEstadoOrden(estado);
        if (estadoOpt.isEmpty()) {
            responceDTO.setMensage("Error: El estado ingresado no es válido");
            return responceDTO;
        }
        EstadoOrdenEntity estados = estadoOpt.get();

        Optional<OrdenTrabajoEntity> ordenOpt = ordenTrabajoRepository.findById(id);
        if (ordenOpt.isEmpty()) {
            responceDTO.setMensage("Error: La orden no existe");
            return responceDTO;
        }
        OrdenTrabajoEntity ordenTrabajoEntity = ordenOpt.get();

        ordenTrabajoEntity.setEstadoOrden(estados);
        ordenTrabajoRepository.save(ordenTrabajoEntity);
        
        responceDTO.setMensage("El estado de la orden se ha actualizado correctamente");
        return responceDTO;
    }

    @Override
    public ObtenerOrdenDTO obtenerPorIdVehiculo(Long idVehiculo) {
        
        Optional<VehiculoEntity> vehiculoOpt = vehiculoRepository.findById(idVehiculo);
        if (vehiculoOpt.isEmpty()) {
            return null;
        }
        VehiculoEntity vehiculo = vehiculoOpt.get();

        List<OrdenTrabajoEntity> ordenes = ordenTrabajoRepository.findByVehiculo(vehiculo);
        
        if (ordenes.isEmpty()) {
            return null;
        }
        
        // Retorna la primera orden encontrada
        return toMapObtenerOrdenDTO(ordenes.get(0));
    }

    @Override
    public List<ObtenerOrdenDTO> obtenerOrdenPorEstado(EstadoOrdenEnums estado) {
        
        Optional<EstadoOrdenEntity> estadoOpt = estadoOrdenRepository.findByEstadoOrden(estado);
        if (estadoOpt.isEmpty()) {
            return new ArrayList<>();
        }
        EstadoOrdenEntity estadoOrden = estadoOpt.get();
        
        List<OrdenTrabajoEntity> ordenes = ordenTrabajoRepository.findByEstadoOrden(estadoOrden);
        
        return ordenes.stream()
            .map(this::toMapObtenerOrdenDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ObtenerOrdenDTO toMapObtenerOrdenDTO(OrdenTrabajoEntity ordenTrabajo) {
        
        ObtenerOrdenDTO ordenDTO = new ObtenerOrdenDTO();

        //Dato de las orden
        ordenDTO.setId(ordenTrabajo.getId());
        ordenDTO.setNumeroOrden(ordenTrabajo.getNuemeroOrden());
        ordenDTO.setFechaEmicion(ordenTrabajo.getFechaEminsion());
        ordenDTO.setFechaCierre(ordenTrabajo.getFechaFin());
        ordenDTO.setFechaEntragaCliente(ordenTrabajo.getFechaEntragaCliente());
        ordenDTO.setEstado(ordenTrabajo.getEstadoOrden().getEstadoOrden());
        ordenDTO.setMotivoCancelacion(ordenTrabajo.getMotivoCancelacion());
        ordenDTO.setPrioridad(ordenTrabajo.getPrioridad().getPrioridad());

        //Datos del empleado
        ordenDTO.setNombreEmpleado(ordenTrabajo.getEmpleado().getUsuario().getNombre() + " " + 
        ordenTrabajo.getEmpleado().getUsuario().getApellido() );

        //Datos del vehiculo
        ordenDTO.setMarcaVehiculo(ordenTrabajo.getVehiculo().getMarca());
        ordenDTO.setModeloVehiculo(ordenTrabajo.getVehiculo().getModelo());
        ordenDTO.setPatenteVehiculo(ordenTrabajo.getVehiculo().getPatente());
        ordenDTO.setKilometroVehiculo(ordenTrabajo.getVehiculo().getKilometraje());
        
        List<DetalleOrdenDTO> listaDetalles = new ArrayList<>();

        if (listaDetalles != null) {

            for (DetalleOrdenEntity detalleOrdenEntity : ordenTrabajo.getDetalleOrden()) {
                
                DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();

                detalleOrdenDTO.setCantidad(detalleOrdenEntity.getCantidad());
                detalleOrdenDTO.setTrabajoRealizado(detalleOrdenEntity.getTrabajoRealizados());
                detalleOrdenDTO.setCodigo(detalleOrdenEntity.getCodigo());
                detalleOrdenDTO.setTipoItem(detalleOrdenEntity.getTipoItem());
                detalleOrdenDTO.setObservaciones(detalleOrdenEntity.getObservaciones());

                listaDetalles.add(detalleOrdenDTO);
            }
        }

        ordenDTO.setDetalleOrden(listaDetalles);

        return ordenDTO;
    }
}
