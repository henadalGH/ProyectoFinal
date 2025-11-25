package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.LoginDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Validation.UsuarioValidacion;
import com.example.wmotorproBack.wmotorBack.Repository.UsuarioRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.AuthService;
import com.example.wmotorproBack.wmotorBack.Servicio.JWTUtilityService;
import com.nimbusds.jose.JOSEException;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTUtilityService jwtUtilityService;

    @Autowired
    private UsuarioValidacion validationUser;

    // ===================== LOGIN CORREGIDO =====================
    @Override
    public HashMap<String, String> login(LoginDTO login) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, JOSEException {

        HashMap<String, String> respuesta = new HashMap<>();

        UsuarioEntity usuario = usuarioRepository.findByEmail(login.getEmail()).orElse(null);

        if (usuario == null) {
            respuesta.put("error", "Usuario no registrado");
            return respuesta;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(login.getPassword(), usuario.getPassword())) {
            respuesta.put("error", "Contraseña incorrecta");
            return respuesta;
        }

        String token = jwtUtilityService.generateJWT(usuario.getId());
        respuesta.put("jwt", token);

        return respuesta;
    }
    // ==========================================================


    // ===================== REGISTRO (SIN MODIFICAR) =====================
    @Override
    public ResponceDTO register(UsuarioEntity usuario) throws Exception {
        try {
            ResponceDTO responce = validationUser.validation(usuario);

            if (responce.getNumOfErrors() > 0) {
                return responce;
            }

            List<UsuarioEntity> getAllListusuario = usuarioRepository.findAll();

            for (UsuarioEntity repeFields : getAllListusuario) {
                if (repeFields != null) {
                    responce.setNumOfErrors(1);
                    responce.setMensage("el usuario ya existe");
                    return responce;
                }
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
    // ====================================================================
}
