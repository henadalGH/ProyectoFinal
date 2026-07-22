package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import lombok.Data;

@Data
public class ClienteDTO {

    private Long id;
    private String direccion;
    private String provincia;
    private String localidad;

    //Datos del usuario
    private String nombre;
    private String apellido;
    private String email;
    private String contacto;
    
    

}
