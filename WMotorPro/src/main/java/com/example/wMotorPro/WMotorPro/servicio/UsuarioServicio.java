package com.example.wMotorPro.WMotorPro.servicio;

import java.util.List;

import com.example.wMotorPro.WMotorPro.modelo.Usuario;



public interface UsuarioServicio {

    public List<Usuario> obtenerUsuarios();
    public Usuario obteneUsuarioPorId(Integer id);
    public Usuario agregarUsuario(Usuario usuario);
    
}
