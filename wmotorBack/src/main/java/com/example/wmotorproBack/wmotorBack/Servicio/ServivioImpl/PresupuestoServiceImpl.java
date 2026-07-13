package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.DetallePresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerPresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.PresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.UltimasFacturasDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.AdminEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetallePresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoOrdenEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoPresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.OrdenTrabajoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoOrdenEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;
import com.example.wmotorproBack.wmotorBack.Repository.AdminRepository;
import com.example.wmotorproBack.wmotorBack.Repository.DetallePresupuestoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoOrdenRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoPresupuestoReposistory;
import com.example.wmotorproBack.wmotorBack.Repository.OrdenTrabajoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.PresuspuestoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.VehiculoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.NumeracionService;
import com.example.wmotorproBack.wmotorBack.Servicio.PresupuestoService;

import jakarta.transaction.Transactional;

@Service
public class PresupuestoServiceImpl implements PresupuestoService {

    @Autowired
    private PresuspuestoRepository presuspuestoRepository;

    @Autowired
    private EstadoPresupuestoReposistory estadoPresupuestoReposistory;

    @Autowired
    private NumeracionService numeracionService;

    @Autowired
    private DetallePresupuestoRepository detallePresupuestoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;



    @Override
@Transactional
public ResponceDTO crearPresupuesto(PresupuestoDTO presupuestoDTO) {

    ResponceDTO response = new ResponceDTO();

    PresupuestoEntity presupuestoEntity = new PresupuestoEntity();
    presupuestoEntity.setFechaRegistro(LocalDate.now());
    presupuestoEntity.setFechaValidez(LocalDate.now().plusDays(15));
    presupuestoEntity.setTipoFactura(presupuestoDTO.getTipoFactura());

    // Estado
    EstadoPresupuestoEntity estado = estadoPresupuestoReposistory
            .findByEstadoPresupuesto(EstadoPresupuestoEnum.PENDIENTE)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

    presupuestoEntity.setEstadoPresupuesto(estado);

    // Admin
    AdminEntity admin = adminRepository.findById(presupuestoDTO.getIdAdmin())
            .orElseThrow(() -> new RuntimeException("Id admin no encontrado"));

    presupuestoEntity.setAdmin(admin);

    // Orden
    OrdenTrabajoEntity orden = ordenTrabajoRepository.findById(presupuestoDTO.getIdOrden())
            .orElseThrow(() -> new RuntimeException("Id orden no encontrado"));

    presupuestoEntity.setOrden(orden);

    EstadoOrdenEntity estadoOrden = estadoOrdenRepository
            .findByEstadoOrden(EstadoOrdenEnums.FACTURADA)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

    orden.setEstadoOrden(estadoOrden);
    ordenTrabajoRepository.save(orden);

    // Vehículo
    VehiculoEntity vehiculo = vehiculoRepository.findById(presupuestoDTO.getIdVehiculo())
            .orElseThrow(() -> new RuntimeException("Id vehículo no encontrado"));

    presupuestoEntity.setVehiculo(vehiculo);

    // Número de presupuesto
    Long numero = numeracionService.generarNumero();
    presupuestoEntity.setNumeroPresupuesto(numero);

    // Detalles
    List<DetallePresupuestoEntity> detalles = new ArrayList<>();
    double subTotalGeneral = 0.0;

    for (DetallePresupuestoDTO detalleDTO : presupuestoDTO.getDetallePresupuesto()) {

        DetallePresupuestoEntity detalleEntity = new DetallePresupuestoEntity();

        detalleEntity.setCantidad(detalleDTO.getCantidad());
        detalleEntity.setDescripcion(detalleDTO.getDescripcion());
        detalleEntity.setPrecioUnitario(detalleDTO.getPrecioUnitario());

        double subTotalLinea = detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();

        detalleEntity.setSubTotal(subTotalLinea);
        detalleEntity.setPresupuesto(presupuestoEntity);

        detalles.add(detalleEntity);

        subTotalGeneral += subTotalLinea;
    }

    // Totales
    double iva = subTotalGeneral * 0.21;
    double totalFinal = subTotalGeneral + iva;

    presupuestoEntity.setDetalle(detalles);
    presupuestoEntity.setSubTotal(subTotalGeneral);
    presupuestoEntity.setIva(iva);
    presupuestoEntity.setTotal(totalFinal);

    // Guardar
    presuspuestoRepository.save(presupuestoEntity);

    response.setMensage("Presupuesto creado correctamente");

    return response;
}



    @Override
    public ResponceDTO cambiarEstadoPresupuesto(EstadoPresupuestoEnum estado, Long idPresupuesto) {
        ResponceDTO response = new ResponceDTO();

        EstadoPresupuestoEntity estadoPre = estadoPresupuestoReposistory.findByEstadoPresupuesto(estado)
        .orElseThrow(() -> new RuntimeException("El estado ingrasado no existe"));

        PresupuestoEntity presupuesto = presuspuestoRepository.findById(idPresupuesto)
        .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        presupuesto.setEstadoPresupuesto(estadoPre);
        presuspuestoRepository.save(presupuesto);

        response.setMensage("EL estado del presupuesto se a actualizado");
        return response;
    }


    @Override
    public List<ObtenerPresupuestoDTO> obtenerPresupuestoPorIdVehiculo(Long id) {
        
        return presuspuestoRepository.findByVehiculoId(id)
        .stream()
        .map( this::toMapPresupuestoDto)
        .collect(Collectors.toList());
    }


    @Override
    public ObtenerPresupuestoDTO toMapPresupuestoDto(PresupuestoEntity presupuesto) {

        ObtenerPresupuestoDTO obtenerPresupuesto  = new ObtenerPresupuestoDTO();

        //Datos del cliente
        obtenerPresupuesto.setNombreCliente(presupuesto.getVehiculo().getCliente().getUsuario().getNombre());
        obtenerPresupuesto.setApellidoCliente(presupuesto.getVehiculo().getCliente().getUsuario().getApellido());
        obtenerPresupuesto.setDireccionCliente(presupuesto.getVehiculo().getCliente().getDireccion());
        obtenerPresupuesto.setCorreoCliente(presupuesto.getVehiculo().getCliente().getUsuario().getEmail());
        obtenerPresupuesto.setContactoCliente(presupuesto.getVehiculo().getCliente().getUsuario().getContacto());

        //Datos Admin
        obtenerPresupuesto.setNombreAdmin(presupuesto.getAdmin().getUsuario().getNombre() + " " + presupuesto.getAdmin().getUsuario().getApellido());


        //Datos del vehiculo
        obtenerPresupuesto.setMarcaVehiculo(presupuesto.getVehiculo().getMarca());
        obtenerPresupuesto.setModeloVehiculo(presupuesto.getVehiculo().getModelo());
        obtenerPresupuesto.setPatenteVehiculo(presupuesto.getVehiculo().getPatente());

        //Datos del presupuesto
        obtenerPresupuesto.setId(presupuesto.getId());
        obtenerPresupuesto.setNumeroPresupuesto(presupuesto.getNumeroPresupuesto());
        obtenerPresupuesto.setFechaRegistro(presupuesto.getFechaRegistro());
        obtenerPresupuesto.setFechaVencimiesto(presupuesto.getFechaValidez());
        obtenerPresupuesto.setObservaciones(presupuesto.getObservaciones());
        obtenerPresupuesto.setTotal(presupuesto.getTotal());
        obtenerPresupuesto.setIva(presupuesto.getIva());
        obtenerPresupuesto.setSubTotal(presupuesto.getSubTotal());
        obtenerPresupuesto.setTipoFacturaDTO(presupuesto.getTipoFactura());
        obtenerPresupuesto.setEstadoPresupuesto(presupuesto.getEstadoPresupuesto().getEstadoPresupuesto());
        


        //Detalle presupuesto
        List<DetallePresupuestoDTO> listaDetalle = new ArrayList<>();

        if (presupuesto.getDetalle() != null) {
            for (DetallePresupuestoEntity detallePresupuesto : presupuesto.getDetalle()) {

            DetallePresupuestoDTO detalle = new DetallePresupuestoDTO();
            detalle.setDescripcion(detallePresupuesto.getDescripcion());
            detalle.setCantidad(detallePresupuesto.getCantidad());
            detalle.setPrecioUnitario(detallePresupuesto.getPrecioUnitario());
            detalle.setSubTotal(detallePresupuesto.getSubTotal());
            

            listaDetalle.add(detalle);
        }
 
        }

        obtenerPresupuesto.setDetallePresupuesto(listaDetalle);
        
        return obtenerPresupuesto;
    }

    @Override
    public ObtenerPresupuestoDTO obtenerPresupuestoPorId(Long id) {

        PresupuestoEntity presupuesto = presuspuestoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado con id: " + id));

        return toMapPresupuestoDto(presupuesto);
    }

    @Override
    @Transactional
    public ResponceDTO actualizarPresupuesto(PresupuestoDTO presupuestoDTO, Long idPresupuesto) {
        ResponceDTO response = new ResponceDTO();

        PresupuestoEntity presupuesto = presuspuestoRepository.findById(idPresupuesto)
            .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado con id: " + idPresupuesto));

        // Actualizar detalles: eliminar antiguos y crear nuevos
        if (presupuesto.getDetalle() != null) {
            detallePresupuestoRepository.deleteAll(presupuesto.getDetalle());
        }

        double total = 0;
        List<DetallePresupuestoEntity> nuevosDetalles = new ArrayList<>();
        for (DetallePresupuestoDTO detalleDto : presupuestoDTO.getDetallePresupuesto()) {
            DetallePresupuestoEntity detalleEntity = new DetallePresupuestoEntity();
            detalleEntity.setCantidad(detalleDto.getCantidad());
            detalleEntity.setDescripcion(detalleDto.getDescripcion());
            detalleEntity.setPrecioUnitario(detalleDto.getPrecioUnitario());
            double subTotal = detalleDto.getCantidad() * detalleDto.getPrecioUnitario();
            detalleEntity.setSubTotal(subTotal);
            detalleEntity.setPresupuesto(presupuesto);
            nuevosDetalles.add(detalleEntity);
            detallePresupuestoRepository.save(detalleEntity);
            total += subTotal;
        }

        presupuesto.setDetalle(nuevosDetalles);
        presupuesto.setTotal(total);

        presuspuestoRepository.save(presupuesto);

        response.setMensage("Presupuesto actualizado correctamente");
        return response;
    }

    @Override
    @Transactional
    public ResponceDTO eliminarPresupuesto(Long idPresupuesto) {
        ResponceDTO response = new ResponceDTO();

        PresupuestoEntity presupuesto = presuspuestoRepository.findById(idPresupuesto)
            .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado con id: " + idPresupuesto));

        // Eliminar detalles primero
        if (presupuesto.getDetalle() != null) {
            detallePresupuestoRepository.deleteAll(presupuesto.getDetalle());
        }

        presuspuestoRepository.delete(presupuesto);

        response.setMensage("Presupuesto eliminado correctamente");
        return response;
    }

    @Override
    public List<ObtenerPresupuestoDTO> obtenerTodosLosPresupuestos() {
        return presuspuestoRepository.findAll()
            .stream()
            .map(this::toMapPresupuestoDto)
            .collect(Collectors.toList());
    } 



    @Override
    public List<UltimasFacturasDTO> obtenerUltimasFactura() {
        return presuspuestoRepository.findTop5ByOrderByFechaRegistroDesc()
                .stream()
                .map(p -> {

                    UltimasFacturasDTO dto = new UltimasFacturasDTO();

                    dto.setNumeroFactura(p.getNumeroPresupuesto());

                    dto.setNombreCliente(
                            p.getVehiculo().getCliente().getUsuario().getNombre() + " " +p.getVehiculo().getCliente().getUsuario().getApellido()
                    );

                    dto.setMonto(p.getTotal());


                    dto.setEstado(
                            p.getEstadoPresupuesto().getEstadoPresupuesto()
                    );

                    // servicio (ejemplo desde detalle)
                    dto.setEstado(p.getEstadoPresupuesto().getEstadoPresupuesto());
                    return dto;
                })
                .toList();
    }



    @Override
    public List<ObtenerPresupuestoDTO> obtenerPresupuestoPorIdCliente(Long idCliente) {
        
        return presuspuestoRepository.findByVehiculoClienteId(idCliente)
            .stream()
            .map(this::toMapPresupuestoDto)
            .collect(Collectors.toList());
    }



    @Override
    public List<ObtenerPresupuestoDTO> obtenerPresupuestPorEstadoAndCliente(Long idCliente,
            EstadoPresupuestoEnum estado) {

            EstadoPresupuestoEntity estadoPre = estadoPresupuestoReposistory.findByEstadoPresupuesto(estado)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

                return presuspuestoRepository.findByVehiculoClienteIdAndEstadoPresupuesto(idCliente, estadoPre)
            .stream()
            .map(this::toMapPresupuestoDto)
            .collect(Collectors.toList());
    }



   @Override
public List<UltimasFacturasDTO> obtenerUltimasFacturaPorCliente(Long idCliente) {

    return presuspuestoRepository
            .findTop5ByVehiculoClienteIdAndEstadoPresupuestoEstadoPresupuestoInOrderByFechaRegistroDesc(
                    idCliente,
                    List.of(EstadoPresupuestoEnum.ENVIADO, EstadoPresupuestoEnum.PAGADO)
            )
            .stream()
            .map(p -> {

                UltimasFacturasDTO dto = new UltimasFacturasDTO();

                dto.setNumeroFactura(p.getNumeroPresupuesto());

                dto.setNombreCliente(
                        p.getVehiculo().getCliente().getUsuario().getNombre()
                        + " "
                        + p.getVehiculo().getCliente().getUsuario().getApellido()
                );

                dto.setMonto(p.getTotal());

                dto.setEstado(
                        p.getEstadoPresupuesto().getEstadoPresupuesto()
                );

                dto.setFechaRegistro(p.getFechaRegistro());

                return dto;
            })
            .toList();
}



   @Override
   public Long contarFacturasPendientes(Long idCliente) {

    EstadoPresupuestoEntity estado = estadoPresupuestoReposistory.findByEstadoPresupuesto(EstadoPresupuestoEnum.ENVIADO)
    .orElseThrow();

    return presuspuestoRepository.countByVehiculoClienteIdAndEstadoPresupuesto(idCliente, estado);
   }
}