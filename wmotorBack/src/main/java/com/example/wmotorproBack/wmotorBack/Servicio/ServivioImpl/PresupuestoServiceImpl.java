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
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.AdminEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetallePresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoPresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;
import com.example.wmotorproBack.wmotorBack.Repository.AdminRepository;
import com.example.wmotorproBack.wmotorBack.Repository.DetallePresupuestoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoPresupuestoReposistory;
import com.example.wmotorproBack.wmotorBack.Repository.PresuspuestoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.TurnoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.VehiculoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.NumeracionPresupuestoService;
import com.example.wmotorproBack.wmotorBack.Servicio.PresupuestoService;

import jakarta.transaction.Transactional;

@Service
public class PresupuestoServiceImpl implements PresupuestoService {

    @Autowired
    private PresuspuestoRepository presuspuestoRepository;

    @Autowired
    private EstadoPresupuestoReposistory estadoPresupuestoReposistory;

    @Autowired
    private NumeracionPresupuestoService numeradorService;

    @Autowired
    private DetallePresupuestoRepository detallePresupuestoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TurnoRepository turnoRepository;


    @Override
    @Transactional
    public ResponceDTO crearPresupuesto(PresupuestoDTO presupuestoDTO) {

        ResponceDTO responce = new ResponceDTO();

        PresupuestoEntity presupuestoEntity = new PresupuestoEntity();
        presupuestoEntity.setFechaRegistro(LocalDate.now());
        presupuestoEntity.setFechaValidez(LocalDate.now().plusDays(15));
        presupuestoEntity.setObservaciones(presupuestoDTO.getObservaciones());

        //Estado del presupuesto
        EstadoPresupuestoEntity estado = estadoPresupuestoReposistory
                .findByEstadoPresupuesto(EstadoPresupuestoEnum.PENDIENTE)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        presupuestoEntity.setEstadoPresupuesto(estado);

        //relacion con admin

        AdminEntity admin = adminRepository.findById(presupuestoDTO.getIdAdmin())
        .orElseThrow(() -> new RuntimeException("Id admin no encontrado"));

        presupuestoEntity.setAdmin(admin);

        //Relacion con vehiculo
        VehiculoEntity vehiculo = vehiculoRepository.findById(presupuestoDTO.getIdVehiculo())
        .orElseThrow(() -> new RuntimeException("id vehiculo no encontrado"));

        presupuestoEntity.setVehiculo(vehiculo);

        // Validar que haya un turno confirmado o en proceso para el vehículo
        List<TurnoEntity> turnos = turnoRepository.findByVehiculo(vehiculo);
        boolean tieneTurnoAceptado = turnos.stream()
            .anyMatch(turno -> turno.getEstado() != null && 
                (turno.getEstado().getEstadoTurno() == EstadoTurnoEnums.CONFIRMADO || 
                 turno.getEstado().getEstadoTurno() == EstadoTurnoEnums.EN_PROCESO));
        
        if (!tieneTurnoAceptado) {
            throw new RuntimeException("No se puede crear presupuesto: debe haber un turno confirmado o en proceso para el vehículo");
        }




        // ✅ numeración (mejor genérica)
        Long numero = numeradorService.generarNumero();
        presupuestoEntity.setNumeroPresupuesto(numero);

       
        double total = 0;
        List<DetallePresupuestoEntity> detalles = new ArrayList<>();

        for (DetallePresupuestoDTO detalleDto : presupuestoDTO.getDetallePresupuesto()) {

            DetallePresupuestoEntity detalleEntity = new DetallePresupuestoEntity();

            detalleEntity.setCantidad(detalleDto.getCantidad());
            detalleEntity.setDescripcion(detalleDto.getDescripcion());
            detalleEntity.setPrecioUnitario(detalleDto.getPrecioUnitario());
            detalleEntity.setTipo(detalleDto.getTipoItem());

            double subTotal = detalleDto.getCantidad() * detalleDto.getPrecioUnitario();
            detalleEntity.setSubTotal(subTotal);

            // relación
            detalleEntity.setPresupuesto(presupuestoEntity);

            total += subTotal;
            detalles.add(detalleEntity);
            detallePresupuestoRepository.save(detalleEntity);
        }

       

        // ✅ ESTO TE FALTABA (CLAVE)
        presupuestoEntity.setDetalle(detalles);
        presupuestoEntity.setTotal(total);

        // guardar
        presuspuestoRepository.save(presupuestoEntity);

        // respuesta básica (podés mejorarla después)
        responce.setMensage("Presupuesto creado correctamente");

        return responce;
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

        //Datos Admin
        obtenerPresupuesto.setNombreAdmin(presupuesto.getAdmin().getUsuario().getNombre() + " " + presupuesto.getAdmin().getUsuario().getApellido());


        //Datos del vehiculo
        obtenerPresupuesto.setMarcaVehiculo(presupuesto.getVehiculo().getMarca());
        obtenerPresupuesto.setModeloVehiculo(presupuesto.getVehiculo().getModelo());

        //Datos del presupuesto
        obtenerPresupuesto.setNumeroPresupuesto(presupuesto.getNumeroPresupuesto());
        obtenerPresupuesto.setFechaRegistro(presupuesto.getFechaRegistro());
        obtenerPresupuesto.setFechaVencimiesto(presupuesto.getFechaValidez());
        obtenerPresupuesto.setObservaciones(presupuesto.getObservaciones());
        obtenerPresupuesto.setTotal(presupuesto.getTotal());


        //Detalle presupuesto
        List<DetallePresupuestoDTO> listaDetalle = new ArrayList<>();

        if (presupuesto.getDetalle() != null) {
            for (DetallePresupuestoEntity detallePresupuesto : presupuesto.getDetalle()) {

            DetallePresupuestoDTO detalle = new DetallePresupuestoDTO();
            detalle.setDescripcion(detallePresupuesto.getDescripcion());
            detalle.setCantidad(detallePresupuesto.getCantidad());
            detalle.setPrecioUnitario(detallePresupuesto.getPrecioUnitario());
            detalle.setTipoItem(detallePresupuesto.getTipo());
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

        // Actualizar observaciones
        presupuesto.setObservaciones(presupuestoDTO.getObservaciones());

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
            detalleEntity.setTipo(detalleDto.getTipoItem());
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

}
