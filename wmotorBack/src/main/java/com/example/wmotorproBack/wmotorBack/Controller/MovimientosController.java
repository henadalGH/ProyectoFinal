package com.example.wmotorproBack.wmotorBack.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.MovimientoFinService;

@RestController
@RequestMapping("/movimiento")
public class MovimientosController {

    @Autowired
    private MovimientoFinService movimientoFinService;

    // ==========================
    // REGISTRAR MOVIMIENTO
    // ==========================
    @PostMapping("/registro")
    public ResponseEntity<ResponceDTO> registrarMovimiento(
            @RequestBody MovimientoDTO movimiento) throws Exception {

        return new ResponseEntity<>(
                movimientoFinService.crearMovimiento(movimiento),
                HttpStatus.CREATED);
    }

    // ==========================
    // OBTENER TODOS
    // ==========================
    @GetMapping
    public ResponseEntity<List<MovimientoFinancieroEntity>> obtenerTodos() {

        return new ResponseEntity<>(
                movimientoFinService.getAllMovimiento(),
                HttpStatus.OK);
    }

    // ==========================
    // BUSCAR POR FECHA
    // ==========================
    @GetMapping("/{fecha}")
    public ResponseEntity<List<MovimientoFinancieroEntity>> obtenerPorFecha(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        return new ResponseEntity<>(
                movimientoFinService.obtenerPorFecha(fecha),
                HttpStatus.OK);
    }

    // ==========================
    // BUSCAR ENTRE FECHAS
    // ==========================
    @GetMapping("/entre")
    public ResponseEntity<List<MovimientoFinancieroEntity>> obtenerEntreFecha(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin) {

        return new ResponseEntity<>(
                movimientoFinService.obtenerEntreFechas(
                        fechaInicio,
                        fechaFin),
                HttpStatus.OK);
    }

    // ==========================
    // BUSCAR POR MES
    // ==========================
    @GetMapping("/mes")
    public ResponseEntity<?> obtenerPorMes(
            @RequestParam int mes,
            @RequestParam int anio) {

        List<MovimientoFinancieroEntity> lista =
                movimientoFinService.obtenerPorMes(
                        mes,
                        anio);

        if (lista.isEmpty()) {

            ResponceDTO response = new ResponceDTO();
            response.setNumOfErrors(1);
            response.setMensage(
                    "No hay movimientos para "
                            + mes + "/" + anio);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(lista);
    }

    // ==========================
    // BUSCAR POR CATEGORIA
    // ==========================
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<MovimientoFinancieroEntity>>
            obtenerPorCategoria(
                    @PathVariable Long idCategoria) {

        return ResponseEntity.ok(
                movimientoFinService
                        .obtenerPorCategoria(idCategoria));
    }

    // ==========================
    // BUSCAR POR TIPO
    // ==========================
    @GetMapping("/tipo")
    public ResponseEntity<List<MovimientoFinancieroEntity>>
            obtenerPorTipo(
                    @RequestParam String tipo) {

        return ResponseEntity.ok(
                movimientoFinService
                        .obtenerPorTipo(tipo));
    }

    // ==========================
    // TOTAL INGRESOS
    // ==========================
    @GetMapping("/total-ingresos")
    public ResponseEntity<Double> totalIngresos(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate desde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate hasta) {

        return ResponseEntity.ok(
                movimientoFinService
                        .totalIngresos(desde, hasta));
    }

    // ==========================
    // TOTAL EGRESOS
    // ==========================
    @GetMapping("/total-egresos")
    public ResponseEntity<Double> totalEgresos(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate desde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate hasta) {

        return ResponseEntity.ok(
                movimientoFinService
                        .totalEgresos(desde, hasta));
    }

    // ==========================
    // BALANCE GENERAL
    // ==========================
    @GetMapping("/balance")
    public ResponseEntity<Double> balance(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate desde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate hasta) {

        return ResponseEntity.ok(
                movimientoFinService
                        .balanceGeneral(desde, hasta));
    }
}