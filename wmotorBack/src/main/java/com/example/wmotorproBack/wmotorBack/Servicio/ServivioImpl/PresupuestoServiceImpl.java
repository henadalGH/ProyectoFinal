package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.DetallePresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.PresupuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.DetallePresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoPresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.PresupuestoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoPresupuestoEnum;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoPresupuestoReposistory;
import com.example.wmotorproBack.wmotorBack.Repository.PresuspuestoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.NumeracionPresupuestoService;
import com.example.wmotorproBack.wmotorBack.Servicio.PresupuestoService;

import jakarta.transaction.Transactional;

@Service
public class PresupuestoServiceImpl implements PresupuestoService {

    @Autowired
    private PresuspuestoRepository presuspuestoRepository;

    @Autowired
    private EstadoPresupuestoReposistory estadoPresupuestoReposistory;

    @Autowired
    private NumeracionPresupuestoService numeradorService;



    @Override
    @Transactional
    public ResponceDTO crearPresupuesto(PresupuestoDTO presupuestoDTO) {

        ResponceDTO responce = new ResponceDTO();

        PresupuestoEntity presupuestoEntity = new PresupuestoEntity();
        presupuestoEntity.setFechaRegistro(LocalDate.now());
        presupuestoEntity.setObservaciones(presupuestoDTO.getObserbaciones());

        EstadoPresupuestoEntity estado = estadoPresupuestoReposistory
                .findByEstadoPresupuesto(EstadoPresupuestoEnum.PENDIENTE)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        presupuestoEntity.setEstadoPresupuesto(estado);

        // ✅ numeración (mejor genérica)
        Long numero = numeradorService.generarNumero();
        presupuestoEntity.setNumeroPresupuesto(numero);

        double total = 0;
        List<DetallePresupuestoEntity> detalles = new ArrayList<>();

        for (DetallePresupuestoDTO detalleDto : presupuestoDTO.getDetallePresupuesto()) {

            DetallePresupuestoEntity detalleEntity = new DetallePresupuestoEntity();

            detalleEntity.setCantidad(detalleDto.getCantidad());
            detalleEntity.setDescripcion(detalleDto.getDescripcion());
            detalleEntity.setPrecioUnitario(detalleDto.getPrescioUnitario());

            double subTotal = detalleDto.getCantidad() * detalleDto.getPrescioUnitario();
            detalleEntity.setSubTotal(subTotal);

            // relación
            detalleEntity.setPresupuesto(presupuestoEntity);

            total += subTotal;
            detalles.add(detalleEntity);
        }

        // ✅ ESTO TE FALTABA (CLAVE)
        presupuestoEntity.setDetalle(detalles);
        presupuestoEntity.setTotal(total);

        // guardar
        presuspuestoRepository.save(presupuestoEntity);

        // respuesta básica (podés mejorarla después)
        responce.setMensage("Presupuesto creado correctamente");

        return responce;
    }
  
    }
