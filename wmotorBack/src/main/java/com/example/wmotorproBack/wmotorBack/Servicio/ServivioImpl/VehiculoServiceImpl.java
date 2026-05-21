package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ClienteEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Repository.ClienteRepository;
import com.example.wmotorproBack.wmotorBack.Repository.VehiculoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.VehiculoService;


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
public VehiculoDTO obtenerVehiculoPorID(Long id) {

    VehiculoEntity  vehiculoEntity = vehiculoRepository.findById(id)
    .orElseThrow(()-> new  RuntimeException("No se encuentra vehiculo registrado"));

    return mapToVehiculoDto(vehiculoEntity);

    
}

    @Override
    public VehiculoDTO mapToVehiculoDto(VehiculoEntity vehiculo) {
        
        VehiculoDTO vehiculoDTO = new VehiculoDTO();

        vehiculoDTO.setId(vehiculo.getId());
        vehiculoDTO.setMarca(vehiculo.getMarca());
        vehiculoDTO.setModelo(vehiculo.getModelo());
        vehiculoDTO.setPatente(vehiculo.getPatente());
        vehiculoDTO.setAnio(vehiculo.getAnio());
        vehiculoDTO.setKilometraje(vehiculo.getKilometraje());
    
        
        return vehiculoDTO;
    }

    @Override
    public List<VehiculoDTO> ontenerVehiculoPorIdCliente(Long id) {

        return vehiculoRepository.findByClienteId(id)
        .stream()
        .map(this::mapToVehiculoDto)
        .toList();
    }

}
