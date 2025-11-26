package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Validation.UsuarioValidacion;
/* import com.example.wmotorproBack.wmotorBack.Repository.RolesRepository; */
import com.example.wmotorproBack.wmotorBack.Repository.UsuarioRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.RegistroService;

@Service
public class RegistroServiceImpl implements RegistroService {
    
@Autowired
private UsuarioRepository usuarioRepository;

/* @Autowired
private RolesRepository rolesRepository; */

@Autowired
    private UsuarioValidacion validationUser;

    @Override
    public ResponceDTO register(UsuarioEntity usuario) throws Exception {
        try {
            ResponceDTO responce = validationUser.validation(usuario);

            if (responce.getNumOfErrors() > 0) {
                return responce;
            }


            if(usuarioRepository.existsByEmail(usuario.getEmail())){
                responce.setNumOfErrors(1);
                responce.setMensage("El usuario ya existe");
                return responce;
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            
            
            usuarioRepository.save(usuario);
            responce.setMensage("El usuario se a creado con exito");

            
            return responce;
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }
}
