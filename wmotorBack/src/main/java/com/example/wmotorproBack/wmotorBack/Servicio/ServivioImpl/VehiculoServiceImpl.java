package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ClienteEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Validation.VehiculoValidacion;
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
    
    @Autowired
    private VehiculoValidacion vehiculoValidacion;

    @Override
    public ResponceDTO agregarVehiculo(VehiculoDTO vehiculoDTO, Long id) throws Exception {
        
        
        try {

            ResponceDTO responceDTO = vehiculoValidacion.validacionVehiculo(vehiculoDTO);

            if (responceDTO.getNumOfErrors() > 0) {
                return responceDTO;
            }

            if (vehiculoRepository.existsByPatente(vehiculoDTO.getPatente())) {
                responceDTO.setNumOfErrors(1);
                responceDTO.setMensage("La patente ya se encuestra registrada");
                return responceDTO;
            }

            ClienteEntity cliente = clienteRepository.getReferenceById(id);

            if (cliente == null) {
                throw new RuntimeException("El id del cliente no existe");
            }

            VehiculoEntity vehiculo = new VehiculoEntity();
            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setAnio(vehiculoDTO.getAnio());
            vehiculo.setPatente(vehiculoDTO.getPatente());
            vehiculo.setKilometraje(vehiculoDTO.getKilometraje());
            vehiculo.setActivo(vehiculo.getActivo());
            vehiculo.setCliente(cliente);

            vehiculoRepository.save(vehiculo);
            responceDTO.setMensage("Vehiculo registrado");
            return responceDTO;
        }
        catch (Exception e) {
            throw new Exception(e.toString());
        }
        
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
    public Boolean eliminarVehiculo(Long id) {
        if (vehiculoRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        throw new RuntimeException("Menu no encontrado");
    }

   @Override
        public void darDeBajaVehiculo(Long idCliente, Long idVehiculo) {

            VehiculoEntity vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

            if (!vehiculo.getCliente().getId_cliente().equals(idCliente)) {
                throw new RuntimeException("El vehiculo no pertenece a este cliente");
            }

            vehiculo.setActivo(false);
            vehiculoRepository.save(vehiculo);
        }


}
