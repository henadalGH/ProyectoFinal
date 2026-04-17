package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;
import lombok.Data;

@Data
public class RegistroDTO {
    
    // Datos comunes del usuario
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String contacto;
    private RolesEnum rol;

    // Cliente
    private String direccion;

    // Empleado
    private CargosEnum cargo;
    private LocalDate fechaNacimieto;
    private String dni;
    private LocalDate fechaIngreso;
    
}
