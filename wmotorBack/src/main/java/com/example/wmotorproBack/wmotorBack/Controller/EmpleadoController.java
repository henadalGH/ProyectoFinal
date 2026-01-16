package com.example.wmotorproBack.wmotorBack.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.EmpleadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.EmpleadoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/todos")
    public ResponseEntity<List<EmpleadoEntity>> getAllEmpleado() {
        return new ResponseEntity<>(empleadoService.getAllEmpleado(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getEmpeadoId(@PathVariable Long id) {

        try {
            EmpleadoDTO empleado = empleadoService.getEmpleadoProId(id);
            return ResponseEntity.ok(empleado);

        } catch (RuntimeException e) {

            ResponceDTO r = new ResponceDTO();
            r.setNumOfErrors(1);
            r.setMensage(e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r);
        }
    }

    
    

}
