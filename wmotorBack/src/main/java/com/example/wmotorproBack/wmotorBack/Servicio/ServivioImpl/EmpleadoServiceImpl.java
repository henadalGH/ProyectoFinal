package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.EmpleadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ModificaEmpleadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;
import com.example.wmotorproBack.wmotorBack.Repository.CargoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.UsuarioRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.EmpleadoService;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Autowired 
    private CargoRepository cargoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<EmpleadoEntity> getAllEmpleado() {
        return empleadoRepository.findAll();
    }

    @Override
    public EmpleadoDTO getEmpleadoProId(Long id) {

        try {
            EmpleadoEntity empleado = empleadoRepository.getReferenceById(id);

            // acá recién puede explotar si no existe
            empleado.getId(); // fuerza la carga

            EmpleadoDTO empleados = new EmpleadoDTO();
            empleados.setId(empleado.getId());
            empleados.setNombre(empleado.getUsuario().getNombre());
            empleados.setApellido(empleado.getUsuario().getApellido());
            empleados.setEmail(empleado.getUsuario().getEmail());
            empleados.setDni(empleado.getDni());
            empleados.setContacto(empleado.getUsuario().getContacto());
            empleados.setFechaIngreso(empleado.getFechaIngreso());
            empleados.setFechaNacimiento(empleado.getFechaNacimiento());
            empleados.setCargo(empleado.getCargo().getCargo());
            empleados.setSueldo(empleado.getCargo().getSueldoBase());

            return empleados;

        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new RuntimeException("Empleado no encontrado con id: " + id);

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener empleado", e);
        }
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleadoCargoMecanico(){
        
        CargosEntity cargos =  cargoRepository.findByCargo(CargosEnum.MECANICO)
        .orElseThrow(() -> new RuntimeException("Cargos no encontrado"));

        return empleadoRepository.findByCargo(cargos)
            .stream()
            .map(this::toMapEmpleado)                                   
            .collect(Collectors.toList());
       }

    @Override
    public EmpleadoDTO toMapEmpleado(EmpleadoEntity empleado) {

        EmpleadoDTO empleados = new EmpleadoDTO();
            empleados.setId(empleado.getId());
            empleados.setNombre(empleado.getUsuario().getNombre());
            empleados.setApellido(empleado.getUsuario().getApellido());
            empleados.setEmail(empleado.getUsuario().getEmail());
            empleados.setDni(empleado.getDni());
            empleados.setActivo(empleado.getActivo());
            empleados.setContacto(empleado.getUsuario().getContacto());
            empleados.setFechaIngreso(empleado.getFechaIngreso());
            empleados.setFechaNacimiento(empleado.getFechaNacimiento());
            empleados.setCargo(empleado.getCargo().getCargo());
            empleados.setSueldo(empleado.getCargo().getSueldoBase());

        return empleados;
    }

    @Override
public ResponceDTO modificarEmpleado(Long id, ModificaEmpleadoDTO empleadoDTO) {

    ResponceDTO responceDTO = new ResponceDTO();

    EmpleadoEntity empleadoEntity = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el empleado"));

    // NOMBRE
    if (empleadoDTO.getNombre() != null && !empleadoDTO.getNombre().isBlank()) {
        empleadoEntity.getUsuario().setNombre(empleadoDTO.getNombre().trim());
    }

    // APELLIDO
    if (empleadoDTO.getApellido() != null && !empleadoDTO.getApellido().isBlank()) {
        empleadoEntity.getUsuario().setApellido(empleadoDTO.getApellido().trim());
    }

    // DNI
    if (empleadoDTO.getDni() != null && !empleadoDTO.getDni().isBlank()) {

        if (!empleadoDTO.getDni().equals(empleadoEntity.getDni())
                && empleadoRepository.existsByDni(empleadoDTO.getDni())) {

            throw new RuntimeException("Ya existe un empleado con ese DNI");
        }

        empleadoEntity.setDni(empleadoDTO.getDni().trim());
    }

    // FECHA DE INGRESO
    if (empleadoDTO.getFechaIngreso() != null) {

        if (empleadoDTO.getFechaIngreso().isAfter(LocalDate.now())) {
            throw new RuntimeException("La fecha de ingreso no puede ser futura");
        }

        empleadoEntity.setFechaIngreso(empleadoDTO.getFechaIngreso());
    }

    // FECHA DE NACIMIENTO
    if (empleadoDTO.getFechaNacimiento() != null) {

        if (empleadoDTO.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new RuntimeException("La fecha de nacimiento no puede ser futura");
        }

        empleadoEntity.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
    }

    // EMAIL
    if (empleadoDTO.getEmail() != null && !empleadoDTO.getEmail().isBlank()) {

        if (!empleadoDTO.getEmail().equalsIgnoreCase(empleadoEntity.getUsuario().getEmail())
                && usuarioRepository.existsByEmail(empleadoDTO.getEmail())) {

            throw new RuntimeException("El email ya se encuentra registrado");
        }

        empleadoEntity.getUsuario().setEmail(empleadoDTO.getEmail().trim());
    }

    // CONTACTO
    if (empleadoDTO.getContacto() != null && !empleadoDTO.getContacto().isBlank()) {
        empleadoEntity.getUsuario().setContacto(empleadoDTO.getContacto().trim());
    }

    // CARGO
    if (empleadoDTO.getCargo() != null) {

        CargosEntity cargo = cargoRepository.findByCargo(empleadoDTO.getCargo())
                .orElseThrow(() -> new RuntimeException("No se encontró el cargo"));

        empleadoEntity.setCargo(cargo);
    }

    empleadoRepository.save(empleadoEntity);

    responceDTO.setMensage("Se ha actualizado el empleado correctamente");

    return responceDTO;
}

   @Override
public ResponceDTO activarODesactivarEmpleado(Boolean activo, Long idEmpleado) {

    ResponceDTO responceDTO = new ResponceDTO();

    EmpleadoEntity empleadoEntity = empleadoRepository.findById(idEmpleado)
            .orElseThrow(() -> new RuntimeException("El id no se encuentra registrado"));

    empleadoEntity.setActivo(activo);

    empleadoRepository.save(empleadoEntity);

    if (activo) {
        responceDTO.setMensage("El empleado fue activado con éxito");
    } else {
        responceDTO.setMensage("El empleado fue desactivado con éxito");
    }

    return responceDTO;
}



}
