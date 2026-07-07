package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;

import lombok.Data;

@Data
public class EmpleadoDTO {

    
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contacto;
    private String dni;
    private Boolean activo;
    private LocalDate fechaIngreso;
    private LocalDate fechaNacimiento;
    private CargosEnum cargo;
    private Double sueldo;
   
}
