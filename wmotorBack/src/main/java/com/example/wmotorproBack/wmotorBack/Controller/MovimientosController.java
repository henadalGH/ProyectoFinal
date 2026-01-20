package com.example.wmotorproBack.wmotorBack.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;
import com.example.wmotorproBack.wmotorBack.Servicio.MovimientoFinService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/movimiento")
public class MovimientosController {


    @Autowired
    private MovimientoFinService movimientoFinService;

    @PostMapping("/registro")
    public ResponseEntity<ResponceDTO> registrarMovimiento(@RequestBody MovimientoDTO movimiento, @RequestParam MovimientosEnum movimientosEnum) throws Exception {
        
        return new ResponseEntity<>(movimientoFinService.crearMovimiento(movimiento, movimientosEnum), HttpStatus.CREATED);
    }


    @GetMapping("/{fecha}")
    public ResponseEntity<List<MovimientoFinancieroEntity>> obtenerPorFecha(@PathVariable
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate fecha) {
        return new ResponseEntity<>(movimientoFinService.obtenerPorFecha(fecha), HttpStatus.OK);
    }

    @GetMapping("/entre")
    public ResponseEntity<List<MovimientoFinancieroEntity>> obtenrEntreFecha(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
        ) {
        return new ResponseEntity<>(movimientoFinService.obterEntreFechas(fechaInicio, fechaFin), HttpStatus.OK);
    }


    @GetMapping("/mes")
    public ResponseEntity<?> obtenerPorMes(@RequestParam int mes, @RequestParam int anio) {
        
        List<MovimientoFinancieroEntity> lista = movimientoFinService.obtenrPorMes(mes, anio);

        if (lista.isEmpty()) {
            
            ResponceDTO responce = new ResponceDTO();

            responce.setNumOfErrors(1);
            responce.setMensage("No hay datos del mes solicitado: " + " " + mes + "/" + anio);
            
            return new ResponseEntity<>(responce, HttpStatus.OK);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    
    
    
    

}
