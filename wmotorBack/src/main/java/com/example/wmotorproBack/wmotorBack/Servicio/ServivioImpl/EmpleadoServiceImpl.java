package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

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
import com.example.wmotorproBack.wmotorBack.Servicio.EmpleadoService;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Autowired 
    private CargoRepository cargoRepository;

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
            .orElseThrow(() -> new RuntimeException("No se encontro al empleado"));

    // EMAIL
    if (empleadoDTO.getEmail() != null && !empleadoDTO.getEmail().isBlank()) {
        empleadoEntity.getUsuario().setEmail(empleadoDTO.getEmail());
    }

    // CONTACTO
    if (empleadoDTO.getContacto() != null && !empleadoDTO.getContacto().isBlank()) {
        empleadoEntity.getUsuario().setContacto(empleadoDTO.getContacto());
    }

    // CARGO
    if (empleadoDTO.getCargo() != null) {

        CargosEntity cargosEntity = cargoRepository.findByCargo(empleadoDTO.getCargo())
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));

        empleadoEntity.setCargo(cargosEntity);
    }

    empleadoRepository.save(empleadoEntity);

    responceDTO.setMensage("Se ha actualizado un empleado correctamente");

    return responceDTO;
}




}
