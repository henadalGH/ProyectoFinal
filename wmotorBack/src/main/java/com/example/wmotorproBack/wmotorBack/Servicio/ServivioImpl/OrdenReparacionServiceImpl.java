package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.CancelarOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.DetalleOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerOrdenDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerOrdenReasignarDTO;
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
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.TipoItemEnums;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoOrdenRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoTurnoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.OrdenTrabajoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.PrioridadRepository;
import com.example.wmotorproBack.wmotorBack.Repository.RepuestoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.TurnoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.NumeracionService;
import com.example.wmotorproBack.wmotorBack.Servicio.OrdenReparacionService;

@Service
public class OrdenReparacionServiceImpl implements OrdenReparacionService {

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

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Override
    public ResponceDTO crearReparacion(OrdenReparacionDTO orden) {
        ResponceDTO response = new ResponceDTO();

        OrdenTrabajoEntity ordenTrabajoEntity = new OrdenTrabajoEntity();
        ordenTrabajoEntity.setFechaEminsion(LocalDate.now());
        ordenTrabajoEntity.setMotivoCancelacion(orden.getMotivoCancelacion());
        ordenTrabajoEntity.setFechaFin(orden.getFechaCierre());
        ordenTrabajoEntity.setFechaEntragaCliente(orden.getFechaEntragaCliente());

        // Estado de la orden
        Optional<EstadoOrdenEntity> estadoOpt = estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.ASIGNADA);
        if (estadoOpt.isEmpty()) {
            response.setMensage("Error: Estado ASIGNADA no encontrado");
            return response;
        }
        ordenTrabajoEntity.setEstadoOrden(estadoOpt.get());

        // Empleado
        Optional<EmpleadoEntity> empleadoOpt = empleadoRepository.findById(orden.getIdEmpleado());
        if (empleadoOpt.isEmpty()) {
            response.setMensage("Error: Empleado no encontrado");
            return response;
        }
        ordenTrabajoEntity.setEmpleado(empleadoOpt.get());

        // Prioridad
        Optional<PrioridadEntity> prioridadOpt = prioridadRepository.findByPrioridad(PrioridadEnum.MEDIA);
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
        if (orden.getDetalleOrden() != null) {
            for (DetalleOrdenDTO detalle : orden.getDetalleOrden()) {
                DetalleOrdenEntity detalleEntity = new DetalleOrdenEntity();
                detalleEntity.setTrabajoRealizados(detalle.getTrabajoRealizado());
                detalleEntity.setCantidad(detalle.getCantidad());
                detalleEntity.setCodigo(detalle.getCodigo());
                detalleEntity.setTipoItem(detalle.getTipoItem());
                detalleEntity.setOrden(ordenTrabajoEntity);

                detalles.add(detalleEntity);
            }
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
                .map(this::toMapObtenerOrdenDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ObtenerOrdenDTO obtnerOrdenPorId(Long id) {
        Optional<OrdenTrabajoEntity> ordenOpt = ordenTrabajoRepository.findById(id);
        return ordenOpt.map(this::toMapObtenerOrdenDTO).orElse(null);
    }

    @Override
    public ResponceDTO actualizarEstaOrden(EstadoOrdenEnums estado, Long id) {
        ResponceDTO responceDTO = new ResponceDTO();

        Optional<EstadoOrdenEntity> estadoOpt = estadoOrdenRepository.findByEstadoOrden(estado);
        if (estadoOpt.isEmpty()) {
            responceDTO.setMensage("Error: El estado ingresado no es válido");
            return responceDTO;
        }

        Optional<OrdenTrabajoEntity> ordenOpt = ordenTrabajoRepository.findById(id);
        if (ordenOpt.isEmpty()) {
            responceDTO.setMensage("Error: La orden no existe");
            return responceDTO;
        }

        OrdenTrabajoEntity ordenTrabajoEntity = ordenOpt.get();
        ordenTrabajoEntity.setEstadoOrden(estadoOpt.get());
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

        return ordenTrabajoRepository.findByEstadoOrden(estadoOpt.get())
                .stream()
                .map(this::toMapObtenerOrdenDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ObtenerOrdenDTO toMapObtenerOrdenDTO(OrdenTrabajoEntity ordenTrabajo) {
        ObtenerOrdenDTO ordenDTO = new ObtenerOrdenDTO();

        // Datos de la orden
        ordenDTO.setId(ordenTrabajo.getId());
        ordenDTO.setNumeroOrden(ordenTrabajo.getNuemeroOrden());
        ordenDTO.setFechaEmicion(ordenTrabajo.getFechaEminsion());
        ordenDTO.setFechaCierre(ordenTrabajo.getFechaFin());
        ordenDTO.setFechaEntragaCliente(ordenTrabajo.getFechaEntragaCliente());
        
        if (ordenTrabajo.getEstadoOrden() != null) {
            ordenDTO.setEstado(ordenTrabajo.getEstadoOrden().getEstadoOrden());
        }
        ordenDTO.setMotivoCancelacion(ordenTrabajo.getMotivoCancelacion());
        
        if (ordenTrabajo.getPrioridad() != null) {
            ordenDTO.setPrioridad(ordenTrabajo.getPrioridad().getPrioridad());
        }

        // Datos del empleado
        if (ordenTrabajo.getEmpleado() != null && ordenTrabajo.getEmpleado().getUsuario() != null) {
            ordenDTO.setNombreEmpleado(ordenTrabajo.getEmpleado().getUsuario().getNombre() + " " + 
                                       ordenTrabajo.getEmpleado().getUsuario().getApellido());
        }

        // Datos del vehículo y cliente a través del turno
        if (ordenTrabajo.getTurno() != null && ordenTrabajo.getTurno().getVehiculo() != null) {
            VehiculoEntity vehiculo = ordenTrabajo.getTurno().getVehiculo();
            ordenDTO.setMarcaVehiculo(vehiculo.getMarca());
            ordenDTO.setModeloVehiculo(vehiculo.getModelo());
            ordenDTO.setPatenteVehiculo(vehiculo.getPatente());
            ordenDTO.setKilometroVehiculo(vehiculo.getKilometraje());
            ordenDTO.setIdVehiculo(vehiculo.getId());

            if (vehiculo.getCliente() != null && vehiculo.getCliente().getUsuario() != null) {
                ordenDTO.setNombreCliente(vehiculo.getCliente().getUsuario().getNombre() + " " +
                                           vehiculo.getCliente().getUsuario().getApellido());
                ordenDTO.setContacto(vehiculo.getCliente().getUsuario().getContacto());
                ordenDTO.setEmail(vehiculo.getCliente().getUsuario().getEmail());
            }
        }

        // Mapeo de Detalle y consulta de Precio para Repuestos
        List<DetalleOrdenDTO> listaDetalles = new ArrayList<>();
        if (ordenTrabajo.getDetalleOrden() != null) {
            for (DetalleOrdenEntity detalleOrdenEntity : ordenTrabajo.getDetalleOrden()) {
                DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
                detalleOrdenDTO.setCantidad(detalleOrdenEntity.getCantidad());
                detalleOrdenDTO.setTrabajoRealizado(detalleOrdenEntity.getTrabajoRealizados());
                detalleOrdenDTO.setCodigo(detalleOrdenEntity.getCodigo());
                detalleOrdenDTO.setTipoItem(detalleOrdenEntity.getTipoItem());

                if (detalleOrdenDTO.getTipoItem() == TipoItemEnums.REPUESTO && detalleOrdenDTO.getCodigo() != null) {
                    Double precio = repuestoRepository.findByCodigo(detalleOrdenDTO.getCodigo())
                            .map(repuesto -> repuesto.getPrecioVenta())
                            .orElse(0.0); // O manejar excepción según tu caso de negocio

                    detalleOrdenDTO.setPrecioUnitario(precio);
                }

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
                .orElseThrow(() -> new RuntimeException("Estado no encontrado asignada"));

        EstadoTurnosEntity estado = estadoTurnoRepository.findByEstadoTurno(EstadoTurnoEnums.ASIGNADO_ORDEN)
                .orElseThrow(() -> new RuntimeException("Estado del turno no encontrado"));

        Long numeroOrden = numeracionService.generarNumeroOrden();

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
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con el ID: " + idEmpleado));

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
                if (vehiculo.getCliente() != null && vehiculo.getCliente().getUsuario() != null) {
                    ordenTrabajoEmpleado.setNombreCliente(
                            vehiculo.getCliente().getUsuario().getNombre() + " " + 
                            vehiculo.getCliente().getUsuario().getApellido());
                    ordenTrabajoEmpleado.setContacto(vehiculo.getCliente().getUsuario().getContacto());
                    ordenTrabajoEmpleado.setEmail(vehiculo.getCliente().getUsuario().getEmail());
                }

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
                ordenTrabajoEmpleado.setKiometraje(null);
            }

            if (turno.getServicio() != null) {
                ordenTrabajoEmpleado.setNombreServicio(turno.getServicio().getNombre());
            }

            ordenTrabajoEmpleado.setDescripcionProblema(turno.getDescripcion());
        }

        // Mapeo e inclusión de precios para los detalles que vea el empleado
        if (orden.getDetalleOrden() != null) {
            List<DetalleOrdenDTO> listaDetalles = new ArrayList<>();
            for (DetalleOrdenEntity detalleEntity : orden.getDetalleOrden()) {
                DetalleOrdenDTO detalleDTO = new DetalleOrdenDTO();
                detalleDTO.setCantidad(detalleEntity.getCantidad());
                detalleDTO.setTrabajoRealizado(detalleEntity.getTrabajoRealizados());
                detalleDTO.setCodigo(detalleEntity.getCodigo());
                detalleDTO.setTipoItem(detalleEntity.getTipoItem());

                if (detalleDTO.getTipoItem() == TipoItemEnums.REPUESTO && detalleDTO.getCodigo() != null) {
                    Double precio = repuestoRepository.findByCodigo(detalleDTO.getCodigo())
                            .map(repuesto -> repuesto.getPrecioVenta())
                            .orElse(0.0);

                    detalleDTO.setPrecioUnitario(precio);
                }

                listaDetalles.add(detalleDTO);
            }
            // Si tu OrdenTrabajoEmpleadoDTO tiene el atributo setDetalleOrden, se asigna aquí:
            // ordenTrabajoEmpleado.setDetalleOrden(listaDetalles);
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
                .orElseThrow(() -> new RuntimeException("El id de la orden no se encuentra"));

        EstadoOrdenEntity estado = estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.CANCELADA)
                .orElseThrow(() -> new RuntimeException("Estado CANCELADA no encontrado"));

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

    @Override
    public Long obtenerOrdenTerminadaParaFecturar() {
        EstadoOrdenEntity estado = estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.PARA_FACTURAR)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        return ordenTrabajoRepository.countByEstadoOrden(estado);
    }

    @Override
    public List<ObtenerOrdenDTO> ontenerOrdenesSinTerminarPorEmpeado(Long idEmpleado, EstadoOrdenEnums estadoEnums) {
        EstadoOrdenEntity estado = estadoOrdenRepository.findByEstadoOrden(estadoEnums)
                .orElseThrow(() -> new RuntimeException("Estado ingresado no encontrado"));

        return ordenTrabajoRepository.findByEmpleadoIdAndEstadoOrden(idEmpleado, estado)
                .stream()
                .map(this::toMapObtenerOrdenDTO)
                .collect(Collectors.toList());
    }

   @Override
    @Transactional
    public ResponceDTO reasignarOrdenEmpleado(Long idOrden, Long idNuevoEmpleado) {
        OrdenTrabajoEntity orden = ordenTrabajoRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + idOrden));

        EmpleadoEntity nuevoEmpleado = empleadoRepository.findById(idNuevoEmpleado)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + idNuevoEmpleado));

        // Usamos empleadoOrigen para que coincida con tu entidad
        if (orden.getEmpleadoOrigen() == null) {
            orden.setEmpleadoOrigen(orden.getEmpleado());
        }

        orden.setEmpleado(nuevoEmpleado);
        ordenTrabajoRepository.save(orden);

        // Ajusta la respuesta según los constructores/setters de tu ResponceDTO
        ResponceDTO response = new ResponceDTO();
        // response.setMensaje("Orden reasignada correctamente"); 
        return response; 
    }

   @Override
   public List<ObtenerOrdenReasignarDTO> obtenerOrdenPendientes() {
    
        EstadoOrdenEntity estadoOrdenEntity = estadoOrdenRepository.findByEstadoOrden(EstadoOrdenEnums.ASIGNADA)
        .orElseThrow();

        return ordenTrabajoRepository.findByEstadoOrden(estadoOrdenEntity)
        .stream()
        .map(this::toMapObtenerOrdenReasignarDTO)
        .collect(Collectors.toList());
   }

   @Override
   public ObtenerOrdenReasignarDTO toMapObtenerOrdenReasignarDTO(OrdenTrabajoEntity ordenTrabajoEntity) {
        
    ObtenerOrdenReasignarDTO reasignarDTO = new ObtenerOrdenReasignarDTO();

    reasignarDTO.setNombreCompletoEmpelado(ordenTrabajoEntity.getEmpleado().getUsuario().getNombre()
    + " " + ordenTrabajoEntity.getEmpleado().getUsuario().getApellido());
    reasignarDTO.setFecha(ordenTrabajoEntity.getFechaEminsion());
    reasignarDTO.setNombreServicio(ordenTrabajoEntity.getTurno().getServicio().getNombre());
    reasignarDTO.setVehiculo(ordenTrabajoEntity.getTurno().getVehiculo().getMarca()+ " " +
    ordenTrabajoEntity.getTurno().getVehiculo().getModelo());
    reasignarDTO.setPatente(ordenTrabajoEntity.getTurno().getVehiculo().getPatente());
    

    return reasignarDTO;
   }
}