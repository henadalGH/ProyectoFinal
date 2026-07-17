package com.example.wmotorproBack.wmotorBack.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoStockBajoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.StockRepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Servicio.RepuestoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/repuesto") 
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;

    @GetMapping("/obtenerRepuestos")
    public ResponseEntity<List<RepuestoDTO>> obtenerTodoLosRepuestos() {
        return new ResponseEntity<List<RepuestoDTO>>(repuestoService.obtenerRepuesto(), HttpStatus.OK);
    }

    @GetMapping("/obtenerRepuesto/{idRepuesto}")
    public ResponseEntity<RepuestoDTO> obtenerRepuestoPorId(@PathVariable Long idRepuesto) {
        return new ResponseEntity<RepuestoDTO>(repuestoService.obtenerRepuestoPorId(idRepuesto), HttpStatus.OK);
    }

    @PutMapping("aumentarStock/{idRepuesto}")
    public ResponseEntity<ResponceDTO> aumentarStock(@PathVariable Long idRepuesto, @RequestBody StockRepuestoDTO stockRepuestoDTO) {
        
        return  new ResponseEntity<ResponceDTO>(repuestoService.aumentarStok(idRepuesto, stockRepuestoDTO), HttpStatus.OK);
    }

    @PutMapping("disminuirStock/{idRepuesto}")
    public ResponseEntity<ResponceDTO> DisminuirStock(@PathVariable Long idRepuesto, @RequestBody StockRepuestoDTO stockRepuestoDTO) {
        
        return  new ResponseEntity<ResponceDTO>(repuestoService.disminuirStock(idRepuesto, stockRepuestoDTO), HttpStatus.OK);
    }

    @PutMapping("activaDesacticaRepuesto/{idRepuesto}")
    public ResponseEntity<ResponceDTO> activarDesactivarRepuesto(@PathVariable Long idRepuesto, @RequestBody Boolean actiBoolean) {
    
        return new ResponseEntity<ResponceDTO>(repuestoService.activaDesactivaRepuesto(idRepuesto, actiBoolean), HttpStatus.OK);
    }

    @GetMapping("/obtenerStockBajo")
    public ResponseEntity<List<RepuestoStockBajoDTO>> obtenerRepuestoBajo() {
        return new ResponseEntity<List<RepuestoStockBajoDTO>>(repuestoService.obtenerRepuestoBajoStock(), HttpStatus.OK);
    }
    
    
    
    
    

}
