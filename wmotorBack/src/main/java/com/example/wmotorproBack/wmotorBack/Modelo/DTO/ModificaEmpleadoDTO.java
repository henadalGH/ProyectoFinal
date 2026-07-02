package com.example.wmotorproBack.wmotorBack.Modelo.DTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.CargosEnum;
import lombok.Data;

@Data
public class ModificaEmpleadoDTO {

    private String email;
    private CargosEnum cargo;
    private String contacto;

}
