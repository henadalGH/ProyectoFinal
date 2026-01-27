package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.EmpleadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.EmpleadoService;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepository;

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

}
