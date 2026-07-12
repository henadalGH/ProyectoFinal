package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.CancelarOrdenDTO;
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
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoClienteCasualEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.PrioridadEnum;
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

    // Estado de la orden
    Optional<EstadoOrdenEntity> estadoOpt =
            estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.ASIGNADA);

    if (estadoOpt.isEmpty()) {
        response.setMensage("Error: Estado ASIGNADA no encontrado");
        return response;
    }

    ordenTrabajoEntity.setEstadoOrden(estadoOpt.get());

    // Empleado
    Optional<EmpleadoEntity> empleadoOpt =
            empleadoRepository.findById(orden.getIdEmpleado());

    if (empleadoOpt.isEmpty()) {
        response.setMensage("Error: Empleado no encontrado");
        return response;
    }

    ordenTrabajoEntity.setEmpleado(empleadoOpt.get());

    // Prioridad
    Optional<PrioridadEntity> prioridadOpt =
            prioridadRepository.findByPrioridad(PrioridadEnum.MEDIA);

    if (prioridadOpt.isEmpty()) {
        response.setMensage("Error: Prioridad MEDIA no encontrada");
        return response;
    }

    ordenTrabajoEntity.setPrioridad(prioridadOpt.get());

    // Número de orden
    Long numeroOrden = numeracionService.generarNumeroOrden();
    ordenTrabajoEntity.setNuemeroOrden(numeroOrden);

    // Detalles
    List<DetalleOrdenEntity> detalles = new ArrayList<>();

    for (DetalleOrdenDTO detalle : orden.getDetalleOrden()) {

        DetalleOrdenEntity detalleEntity = new DetalleOrdenEntity();
        detalleEntity.setTrabajoRealizados(detalle.getTrabajoRealizado());
        detalleEntity.setCantidad(detalle.getCantidad());
        detalleEntity.setCodigo(detalle.getCodigo());
        detalleEntity.setTipoItem(detalle.getTipoItem());

        // Relación con la orden
        detalleEntity.setOrden(ordenTrabajoEntity);

        detalles.add(detalleEntity);
    }

    ordenTrabajoEntity.setDetalleOrden(detalles);

    // Guarda la orden y, con CascadeType.ALL, también los detalles
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

    TurnoEntity turno = orden.getTurno();

    if (turno != null) {

        // Cliente registrado
        if (turno.getVehiculo() != null) {

            VehiculoEntity vehiculo = turno.getVehiculo();

            ordenTrabajoEmpleado.setNombreCliente(
                    vehiculo.getCliente().getUsuario().getNombre() + " "
                            + vehiculo.getCliente().getUsuario().getApellido());

            ordenTrabajoEmpleado.setContacto(vehiculo.getCliente().getUsuario().getContacto());
            ordenTrabajoEmpleado.setEmail(vehiculo.getCliente().getUsuario().getEmail());

            ordenTrabajoEmpleado.setMarca(vehiculo.getMarca());
            ordenTrabajoEmpleado.setModelo(vehiculo.getModelo());
            ordenTrabajoEmpleado.setPatente(vehiculo.getPatente());
            ordenTrabajoEmpleado.setKiometraje(vehiculo.getKilometraje());

        }
        // Cliente casual
        else if (turno.getTurnoClienteCasualEntity() != null) {

            TurnoClienteCasualEntity casual = turno.getTurnoClienteCasualEntity();

            ordenTrabajoEmpleado.setNombreCliente(casual.getNombreCliente());
            ordenTrabajoEmpleado.setContacto(casual.getContactoCliente());
            ordenTrabajoEmpleado.setEmail(casual.getEmail());

            ordenTrabajoEmpleado.setMarca(casual.getMarcaVehiculo());
            ordenTrabajoEmpleado.setModelo(casual.getModeloVehiculo());
            ordenTrabajoEmpleado.setPatente(casual.getPatenteVehiculo());

            // Los clientes casuales no tienen kilometraje
            ordenTrabajoEmpleado.setKiometraje(null);
        }

        if (turno.getServicio() != null) {
            ordenTrabajoEmpleado.setNombreServicio(turno.getServicio().getNombre());
        }

        ordenTrabajoEmpleado.setDescripcionProblema(turno.getDescripcion());
    }

    if (orden.getPrioridad() != null) {
        ordenTrabajoEmpleado.setPrioridad(orden.getPrioridad().getPrioridad());
    }

    if (orden.getEstadoOrden() != null) {
        ordenTrabajoEmpleado.setEstado(orden.getEstadoOrden().getEstadoOrden());
    }

    ordenTrabajoEmpleado.setFecha(orden.getFechaEminsion());

    return ordenTrabajoEmpleado;
}


    @Override
    public ResponceDTO motivoCancelacion(Long idOrden, CancelarOrdenDTO cancelar) {
        ResponceDTO responceDTO = new ResponceDTO();

        OrdenTrabajoEntity orden = ordenTrabajoRepository.findById(idOrden)
        .orElseThrow(() -> new RuntimeException("el id de la orden no se encuentra"));

        EstadoOrdenEntity estado = estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.CANCELADA)
        .orElseThrow();

        orden.setEstadoOrden(estado);
        orden.setMotivoCancelacion(cancelar.getMotivoCAncelacion());
        ordenTrabajoRepository.save(orden);

        responceDTO.setMensage("Orden cancelada correctamente");
        return responceDTO;
    }


    @Override
    public List<OrdenTrabajoEmpleadoDTO> ordenesfuturasIdempleado(Long idEmpleado, LocalDate fecha) {
        
        return ordenTrabajoRepository.findByEmpleadoIdAndFechaEminsionGreaterThanEqual(idEmpleado, fecha)
            .stream()
            .map(this::toOrdenTrabajoEmpleadoDTO)
            .collect(Collectors.toList());
    
    }


    @Override
    public List<OrdenTrabajoEmpleadoDTO> obtenerHistorialOrdenes(Long idEmpleado) {
     
        return ordenTrabajoRepository.findTop15ByEmpleadoIdOrderByFechaEminsionDesc(idEmpleado)
            .stream()
            .map(this::toOrdenTrabajoEmpleadoDTO)
            .collect(Collectors.toList());
    }
}
