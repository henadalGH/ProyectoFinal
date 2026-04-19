package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ClienteEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoTurnosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ServicioEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;
import com.example.wmotorproBack.wmotorBack.Repository.ClienteRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EstadoTurnoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.ServicioRepository;
import com.example.wmotorproBack.wmotorBack.Repository.VehiculoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.TurnoService;

@Service
public class turnoServiceImpl implements TurnoService{


    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstadoTurnoRepository estadoTurnoRepository;



    @Override
    public TurnosDTO creaTurnosDTO(TurnosDTO turno) {

        TurnosDTO turnos = new TurnosDTO();
        turnos.setDescripcion(turno.getDescripcion());

        ServicioEntity servicio = servicioRepository.getReferenceById(turno.getIdServicio());
        turnos.setIdServicio(servicio.getId());

        VehiculoEntity vehiculo = vehiculoRepository.getReferenceById(turno.getIdVehiculo());
        turnos.setIdVehiculo(vehiculo.getId());

        ClienteEntity cliente = clienteRepository.getReferenceById(turno.getIdCliente());
        turno.setIdCliente(cliente.getId());

        EstadoTurnosEntity estado = estadoTurnoRepository
        .findByEstadoTurno(EstadoTurnoEnums.PENDIENTE)
        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        turnos.setEstado(estado.getEstadoTurno());

        return turnos;
        
    }

}
