package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import java.time.LocalDate;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;
import lombok.Data;

@Data
public class ModificaEmpleadoDTO {

    private String email;
    private CargosEnum cargo;
    private String contacto;
     private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaIngreso;
    private LocalDate fechaNacimiento;

}
