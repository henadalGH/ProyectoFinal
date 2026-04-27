package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;


import com.example.wmotorproBack.wmotorBack.Repository.TurnoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnoPendenteAsignacionDto;
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


    @Override
    public TurnoPendenteAsignacionDto toMapTurnoDto(TurnoEntity turno) {

        TurnoPendenteAsignacionDto turnoDto = new TurnoPendenteAsignacionDto();

        turnoDto.setId(turno.getId());
        turnoDto.setDescripcion(turno.getDescripcion());
        turnoDto.setIdVehiculo(turno.getVehiculo().getId());
        turnoDto.setMarca(turno.getVehiculo().getMarca());
        turnoDto.setModelo(turno.getVehiculo().getModelo());
        turnoDto.setNombreCliente(turno.getVehiculo().getCliente().getUsuario().getNombre());
        turnoDto.setApellidoCliente(turno.getVehiculo().getCliente().getUsuario().getApellido());
        turnoDto.setContacto(turno.getVehiculo().getCliente().getUsuario().getContacto());
        turnoDto.setNombreServicio(turno.getServicio().getNombre());
    
        return turnoDto;
    }


    @Override
    public List<TurnoPendenteAsignacionDto> obtenerTodosTurnosPendienteAsignacion() {
        // 1. Obtenemos el objeto de estado de la base de datos
        EstadoTurnosEntity estadoPendiente = estadoTurnoRepository.findByEstadoTurno(EstadoTurnoEnums.PENDIENTE_ASIGNACION)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        // 2. Filtramos la lista completa usando Stream
        return turnoRepository.findAll().stream()
                .filter(turno -> turno.getEstado().equals(estadoPendiente)) // Filtrado por el objeto estado
                .map(this::toMapTurnoDto)                                   // Conversión a DTO
                .collect(Collectors.toList());
    }
    

    @Override
    public TurnoEntity asignarFecha(Long idTurno, LocalDateTime fecha) {
        TurnoEntity turno = turnoRepository.findById(idTurno)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        turno.setFechaHora(fecha);
        return turnoRepository.save(turno);
    }


}
