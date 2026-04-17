package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RegistroDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.AdminEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ClienteEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RolesEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;
import com.example.wmotorproBack.wmotorBack.Repository.AdminRepository;
import com.example.wmotorproBack.wmotorBack.Repository.CargoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.ClienteRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.RolesRepository;
import com.example.wmotorproBack.wmotorBack.Repository.UsuarioRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.RegistroService;

import jakarta.transaction.Transactional;

@Service
public class RegistroServicioImpl implements RegistroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private CargoRepository cargoRepository;


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @Override
    @Transactional
    public ResponceDTO registrarUsuario(RegistroDTO usuarioDTO) throws Exception {

        ResponceDTO response = new ResponceDTO();

        // =========================
        // VALIDACIONES
        // =========================
        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isBlank())
            throw new Exception("Email obligatorio");

        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            response.setNumOfErrors(1);
            response.setMensage("El email ya existe");
            return response;
        }

        RolesEnum rolEnum = usuarioDTO.getRol();
        CargosEnum cargoEnum = usuarioDTO.getCargo();

        if (rolEnum == null)
            throw new Exception("Debe especificar un rol");


        // =========================
        // CREAR USUARIO
        // =========================
        UsuarioEntity nuevoUsuario = new UsuarioEntity();

        nuevoUsuario.setNombre(usuarioDTO.getNombre());
        nuevoUsuario.setApellido(usuarioDTO.getApellido());
        nuevoUsuario.setEmail(usuarioDTO.getEmail());
        nuevoUsuario.setContacto(usuarioDTO.getContacto());

        // ✅ Encriptación SIMPLE (como vos querías)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        nuevoUsuario.setPassword(encoder.encode(usuarioDTO.getPassword()));


        // =========================
        // ASIGNAR ROL
        // =========================
        RolesEntity rol = rolesRepository.findByNombre(rolEnum)
                .orElseThrow(() -> new Exception("Rol no encontrado: " + rolEnum));

        nuevoUsuario.setRol(rol);

        usuarioRepository.save(nuevoUsuario);


        // =========================
        // RELACIÓN SEGÚN ROL
        // =========================
        switch (rolEnum) {

            case ADMIN -> {
                AdminEntity admin = new AdminEntity();
                admin.setUsuario(nuevoUsuario);
                adminRepository.save(admin);
            }

            case CLIENTE -> {
                ClienteEntity cliente = new ClienteEntity();
                cliente.setDireccion(usuarioDTO.getDireccion());
                cliente.setUsuario(nuevoUsuario);
                clienteRepository.save(cliente);
            }

            case EMPLEADO -> {

                if (cargoEnum == null)
                    throw new Exception("Empleado requiere cargo");

                CargosEntity cargo = cargoRepository.findByCargo(cargoEnum)
                        .orElseThrow(() -> new Exception("Cargo no encontrado: " + cargoEnum));

                EmpleadoEntity empleado = new EmpleadoEntity();
                empleado.setUsuario(nuevoUsuario);
                empleado.setFechaNacimiento(usuarioDTO.getFechaNacimieto());
                empleado.setFechaIngreso(LocalDate.now());
                empleado.setDni(usuarioDTO.getDni());
                empleado.setCargo(cargo);

                empleadoRepository.save(empleado);
            }

            default -> throw new Exception("Rol no soportado");
        }

        response.setMensage("Usuario registrado correctamente");
        return response;
    }
}