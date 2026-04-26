package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;


import com.example.wmotorproBack.wmotorBack.Repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EstadoTurnosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ServicioEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.EstadoTurnoEnums;
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
    private EstadoTurnoRepository estadoTurnoRepository;

    @Autowired
    private TurnoRepository turnoRepository;


    @Override
    public TurnoEntity creaTurnosDTO(TurnosDTO turno) {

        TurnoEntity turnos = new TurnoEntity();
        turnos.setDescripcion(turno.getDescripcion());

        ServicioEntity servicio = servicioRepository.getReferenceById(turno.getIdServicio());
        turnos.setServicio(servicio);

        VehiculoEntity vehiculo = vehiculoRepository.getReferenceById(turno.getIdVehiculo());
        turnos.setVehiculo(vehiculo);

    
        EstadoTurnosEntity estado = estadoTurnoRepository
        .findByEstadoTurno(EstadoTurnoEnums.PENDIENTE_ASIGNACION)
        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        turnos.setEstado(estado);

        return turnoRepository.save(turnos);
        
    }

}
