package com.example.wMotorPro.WMotorPro.servicio.ServicioImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wMotorPro.WMotorPro.modelo.Usuario;
import com.example.wMotorPro.WMotorPro.modelo.dto.GuardarUsuario;
import com.example.wMotorPro.WMotorPro.repository.UsuarioRepository;
import com.example.wMotorPro.WMotorPro.servicio.UsuarioServicio;


@Service
public class UsuarioServicioImpl implements UsuarioServicio{


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obteneUsuarioPorId(Integer id) {
        return usuarioRepository.getReferenceById(id);
    }

    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        
        throw new UnsupportedOperationException("Unimplemented method 'agregUsuario'");
    }

    public Usuario registrOneCustomer(GuardarUsuario nuevoUsuario) {
       //valildatePassword(nuevoUsuario);
        Usuario usuario = new Usuario();
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setApellido(nuevoUsuario.getApellido());
        usuario.setEmail(nuevoUsuario.getEmail());
        usuario.setContrasenia(passwordEncoder.encode(nuevoUsuario.getContrasenia()));
        usuario.setContacto(nuevoUsuario.getContacto());
        return usuarioRepository.save(usuario);
    }
    
}
