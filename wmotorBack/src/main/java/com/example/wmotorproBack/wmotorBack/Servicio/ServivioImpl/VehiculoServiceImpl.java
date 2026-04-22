package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ClienteEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Repository.ClienteRepository;
import com.example.wmotorproBack.wmotorBack.Repository.VehiculoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.VehiculoService;

import lombok.NonNull;

@Service
public class VehiculoServiceImpl implements VehiculoService{

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public VehiculoEntity agregarVehiculo(VehiculoDTO vehiculoDTO) {
        
        if (vehiculoRepository.existsByPatente(vehiculoDTO.getPatente())) {
            throw new RuntimeException("La patente ya se encuestra registrada");
        }

        VehiculoEntity vehiculo = new VehiculoEntity();
        vehiculo.setMarca(vehiculoDTO.getMarca());
        vehiculo.setModelo(vehiculoDTO.getModelo());
        vehiculo.setAnio(vehiculoDTO.getAnio());
        vehiculo.setPatente(vehiculoDTO.getPatente());
        vehiculo.setKilometraje(vehiculoDTO.getKilometraje());

        ClienteEntity cliente = clienteRepository.findById(vehiculoDTO.getIdCliente())
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        vehiculo.setCliente(cliente);


        return vehiculoRepository.save(vehiculo);
    }

    @Override
public ResponceDTO obtenerVehiculoPorID(@NonNull Long id) {

    ResponceDTO response = new ResponceDTO();

    VehiculoEntity vehiculo = vehiculoRepository.findById(id).orElse(null);

    if (vehiculo == null) {
        response.setNumOfErrors(1);
        response.setMensage("Vehículo no encontrado");
        return response;
    }

    response.setNumOfErrors(0);
    response.setMensage("Vehículo obtenido correctamente");
    

    return response;
}

    @Override
    public VehiculoDTO mapToDto(VehiculoEntity vehiculo) {
        
        return new VehiculoDTO(
            vehiculo.getId(),
            vehiculo.getMarca(),
            vehiculo.getModelo(),
            vehiculo.getAnio(),
            vehiculo.getPatente(),
            vehiculo.getKilometraje()
        ); 
    }

    @Override
    public List<VehiculoDTO> ontenerVehiculoPorIdCliente(Long id) {

        return vehiculoRepository.findByClienteId(id)
        .stream()
        .map(this::mapToDto)
        .toList();
    }

}
