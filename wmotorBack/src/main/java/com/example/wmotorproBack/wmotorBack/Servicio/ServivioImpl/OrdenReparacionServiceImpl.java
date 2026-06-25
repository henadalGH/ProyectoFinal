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
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.OrdenTrabajoEmpleadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetalleOrdenEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoOrdenEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoTurnosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PrioridadEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;
import com.example.wmotorproBack.wmotorBack.Repository.DetalleOrdenReposistory;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoOrdenRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoTurnoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.OrdenTrabajoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.PrioridadRepository;
import com.example.wmotorproBack.wmotorBack.Repository.TurnoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.NumeracionService;
import com.example.wmotorproBack.wmotorBack.Servicio.OrdenReparacionService;

@Service
public class OrdenReparacionServiceImpl implements OrdenReparacionService{


    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;

    @Autowired 
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PrioridadRepository prioridadRepository;

    @Autowired
    private NumeracionService numeracionService;

    @Autowired
    private DetalleOrdenReposistory detalleOrdenReposistory;

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private EstadoTurnoRepository estadoTurnoRepository;



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

    
        //Prioridar de la orden
        Optional<PrioridadEntity> prioridadOpt = prioridadRepository.findByPrioridad(PrioridadEnum.MEDIA);
        if (prioridadOpt.isEmpty()) {
            response.setMensage("Error: Prioridad MEDIA no encontrada");
            return response;
        }
        ordenTrabajoEntity.setPrioridad(prioridadOpt.get());

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

        //Tados del vehiculo
        ordenDTO.setMarcaVehiculo(ordenTrabajo.getTurno().getVehiculo().getMarca());
        ordenDTO.setModeloVehiculo(ordenTrabajo.getTurno().getVehiculo().getModelo());
        ordenDTO.setPatenteVehiculo(ordenTrabajo.getTurno().getVehiculo().getPatente());
        ordenDTO.setKilometroVehiculo(ordenTrabajo.getTurno().getVehiculo().getKilometraje());
        ordenDTO.setIdVehiculo(ordenTrabajo.getTurno().getVehiculo().getId());


        //Datos del cliente
        ordenDTO.setNombreCliente(ordenTrabajo.getTurno().getVehiculo().getCliente().getUsuario().getNombre() + ' ' +
        ordenTrabajo.getTurno().getVehiculo().getCliente().getUsuario().getApellido());
        ordenDTO.setContacto(ordenTrabajo.getTurno().getVehiculo().getCliente().getUsuario().getContacto());
        ordenDTO.setEmail(ordenTrabajo.getTurno().getVehiculo().getCliente().getUsuario().getEmail());
        
        
        List<DetalleOrdenDTO> listaDetalles = new ArrayList<>();

        if (listaDetalles != null) {

            for (DetalleOrdenEntity detalleOrdenEntity : ordenTrabajo.getDetalleOrden()) {
                
                DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();

                detalleOrdenDTO.setCantidad(detalleOrdenEntity.getCantidad());
                detalleOrdenDTO.setTrabajoRealizado(detalleOrdenEntity.getTrabajoRealizados());
                detalleOrdenDTO.setCodigo(detalleOrdenEntity.getCodigo());
                detalleOrdenDTO.setTipoItem(detalleOrdenEntity.getTipoItem());

                listaDetalles.add(detalleOrdenDTO);
            }
        }

        ordenDTO.setDetalleOrden(listaDetalles);

        return ordenDTO;
    }


    @Override
public ResponceDTO asignarOrdeEmpleado(Long idTurno, Long idEmpleado, PrioridadEnum prioridadEnum) {

    ResponceDTO responceDTO = new ResponceDTO();

    TurnoEntity turno = turnoRepository.findById(idTurno)
            .orElseThrow(() -> new RuntimeException("Id del turno no encontrado"));

    PrioridadEntity prioridad = prioridadRepository.findByPrioridad(prioridadEnum)
            .orElseThrow(() -> new RuntimeException("Prioridad no encontrada"));

    EmpleadoEntity empleado = empleadoRepository.findById(idEmpleado)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

    EstadoOrdenEntity estadoOrden = estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.ASIGNADA)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

    EstadoTurnosEntity estado = estadoTurnoRepository.findByEstadoTurno(EstadoTurnoEnums.ASIGNADO_ORDEN)
            .orElseThrow();

    Long numeroOrden = numeracionService.generarNumeroOrden();

    // Modificas el turno existente
    turno.setEstado(estado);

    OrdenTrabajoEntity orden = new OrdenTrabajoEntity();
    orden.setTurno(turno);
    orden.setNuemeroOrden(numeroOrden);
    orden.setEmpleado(empleado);
    orden.setFechaEminsion(LocalDate.now());
    orden.setPrioridad(prioridad);
    orden.setEstadoOrden(estadoOrden);

    turnoRepository.save(turno);
    ordenTrabajoRepository.save(orden);

    responceDTO.setMensage("Orden asignada con éxito");

    return responceDTO;
}

    @Override
    public List<OrdenTrabajoEmpleadoDTO> obtenerOrdenePorEmpleado(Long idEmpleado, LocalDate fecha) {
        
        EmpleadoEntity empleadoEntity = empleadoRepository.findById(idEmpleado)
        .orElseThrow();
        
        return ordenTrabajoRepository.findByEmpleadoAndTurnoFechaHora(empleadoEntity, fecha)
            .stream()
            .map(this::toOrdenTrabajoEmpleadoDTO)
            .collect(Collectors.toList());
        
    }


    @Override 
    public OrdenTrabajoEmpleadoDTO toOrdenTrabajoEmpleadoDTO(OrdenTrabajoEntity orden) {
        
        OrdenTrabajoEmpleadoDTO ordenTrabajoEmpleado = new OrdenTrabajoEmpleadoDTO();

        ordenTrabajoEmpleado.setIdOrden(orden.getId());
        ordenTrabajoEmpleado.setNombreCliente(orden.getTurno().getVehiculo().getCliente().getUsuario().getNombre() + 
        orden.getTurno().getVehiculo().getCliente().getUsuario().getApellido());
        ordenTrabajoEmpleado.setContacto(orden.getTurno().getVehiculo().getCliente().getUsuario().getContacto());
        ordenTrabajoEmpleado.setEmail(orden.getTurno().getVehiculo().getCliente().getUsuario().getEmail());
        ordenTrabajoEmpleado.setMarca(orden.getTurno().getVehiculo().getMarca());
        ordenTrabajoEmpleado.setModelo(orden.getTurno().getVehiculo().getModelo());
        ordenTrabajoEmpleado.setPatente(orden.getTurno().getVehiculo().getPatente());
        ordenTrabajoEmpleado.setKiometraje(orden.getTurno().getVehiculo().getKilometraje());
        ordenTrabajoEmpleado.setNombreServicio(orden.getTurno().getServicio().getNombre());
        ordenTrabajoEmpleado.setDescripcionProblema(orden.getTurno().getDescripcion());
        ordenTrabajoEmpleado.setPrioridad(orden.getPrioridad().getPrioridad());
        ordenTrabajoEmpleado.setEstado(orden.getEstadoOrden().getEstadoOrden());


        return ordenTrabajoEmpleado;
    }
}
