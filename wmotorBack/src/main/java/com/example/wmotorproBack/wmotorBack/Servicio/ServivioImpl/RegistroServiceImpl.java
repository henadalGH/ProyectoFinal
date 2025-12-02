package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RegistroDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.AdminEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ClienteEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RolesEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Validation.UsuarioValidacion;
import com.example.wmotorproBack.wmotorBack.Repository.AdminRepository;
import com.example.wmotorproBack.wmotorBack.Repository.ClienteRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.RolesRepository;
import com.example.wmotorproBack.wmotorBack.Repository.UsuarioRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.RegistroService;

@Service
public class RegistroServiceImpl implements RegistroService{

    

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsuarioValidacion usuarioValidacion;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Override
public ResponceDTO registrarUsuario(RegistroDTO usuario, RolesEnum rol) throws Exception{

    try {

        ResponceDTO responce = usuarioValidacion.validation(usuario);

        if (responce.getNumOfErrors() > 0) {
            return responce;
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            responce.setNumOfErrors(1);
            responce.setMensage("El email ya existe");
            return responce;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String passwordEncoder = encoder.encode(usuario.getPassword());

        // 1. Crear usuario
        UsuarioEntity nuevoUsuario = new UsuarioEntity();
        nuevoUsuario.setNombre(usuario.getNombre());
        nuevoUsuario.setApellido(usuario.getApellido());
        nuevoUsuario.setEmail(usuario.getEmail());
        nuevoUsuario.setPassword(passwordEncoder);

        RolesEntity roles = rolesRepository.findByNombre(rol)
        .orElseThrow(() -> new Exception("Rol no encontrado: " + rol));

        nuevoUsuario.setRol(roles);

        // 2. Guardar primero el usuario
        usuarioRepository.save(nuevoUsuario);

        // 3. Crear la relación según el rol
        if(rol == RolesEnum.ADMIN){
            AdminEntity usuarioAdmin = new AdminEntity();
            usuarioAdmin.setUsuario(nuevoUsuario);
            adminRepository.save(usuarioAdmin);
        }

        if(rol == RolesEnum.CLIENTE){
            ClienteEntity cliente = new ClienteEntity();
            cliente.setDireccion(usuario.getDireccion()); // AHORA SI
            cliente.setUsuario(nuevoUsuario);
            clienteRepository.save(cliente);
        }

        if(rol == RolesEnum.EMPLEADO){
            EmpleadoEntity empleardo = new EmpleadoEntity();
            empleardo.setDni(usuario.getDni());
            empleardo.setFechaIngreso(new Date());
            empleardo.setFechaNacimiento(usuario.getFechaNacimieto());
            empleardo.setUsuario(nuevoUsuario);
            empleadoRepository.save(empleardo);
        }

        responce.setMensage("Usuario registrado");
        System.out.println(usuario.getDireccion());
        return responce;

    } catch (Exception e) {
        throw new Exception(e.toString());
    }
}




}
