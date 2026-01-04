package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RegistroDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;
import com.example.wmotorproBack.wmotorBack.Servicio.RegistroService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @PostMapping("/nuevo")
public ResponseEntity<ResponceDTO> nuevoUsuario(
        @RequestBody RegistroDTO registroDTO,
        @RequestParam RolesEnum rolesEnum,
        @RequestParam(required = false) CargosEnum cargoEnum
    ) throws Exception {

    return new ResponseEntity<>(
            registroService.registrarUsuario(registroDTO, rolesEnum, cargoEnum),
            HttpStatus.CREATED
    );
}

    
}
