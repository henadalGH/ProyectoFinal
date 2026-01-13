package com.example.wmotorproBack.wmotorBack.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.VehiculoService;

import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping("/agregar/{id}")
    public ResponseEntity<ResponceDTO> agregarVehiculo(
        @RequestBody VehiculoDTO vehiculoDTO, 
        @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(
            vehiculoService.agregarVehiculo(vehiculoDTO , id), HttpStatus.CREATED
        );
    }
    

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponceDTO> obtener(@PathVariable @NotNull Long id) {
    return ResponseEntity.ok(vehiculoService.obtenerVehiculoPorID(id));
    }

    @PutMapping("/clientes/{idCliente}/vehiculos/{idVehiculo}/baja")
        public ResponseEntity<Void> darDeBajaVehiculo(
                @PathVariable Long idCliente,
                @PathVariable Long idVehiculo) {

            vehiculoService.darDeBajaVehiculo(idCliente, idVehiculo);
            return ResponseEntity.noContent().build(); // 204
        }

        
        

}
