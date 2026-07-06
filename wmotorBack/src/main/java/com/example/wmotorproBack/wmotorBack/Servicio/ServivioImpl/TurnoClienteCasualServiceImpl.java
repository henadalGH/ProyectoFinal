package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.TurnoClienteCasualDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.TurnoClienteCasualEntity;
import com.example.wmotorproBack.wmotorBack.Servicio.TurnoClienteCasualService;

@Service
public class TurnoClienteCasualServiceImpl implements TurnoClienteCasualService{

    @Override
    public ResponceDTO crearTrunoClienteCasual(TurnoClienteCasualDTO turnoCasual) {

        ResponceDTO responceDTO = new ResponceDTO();

        TurnoClienteCasualEntity clienteCasualEntity = new TurnoClienteCasualEntity();
        clienteCasualEntity.setNombreCliente(turnoCasual.getNombreCliente());
        clienteCasualEntity.setMarcaVehiculo(turnoCasual.getMarcaVehiculo());
        clienteCasualEntity.setModeloVehiculo(turnoCasual.getModeloVehiculo());
        clienteCasualEntity.setPatenteVehiculo(turnoCasual.getPatenteVehiculo());

        

        


        return responceDTO;
    }

}
