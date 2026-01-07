package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ClienteDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ClienteEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;
import com.example.wmotorproBack.wmotorBack.Repository.ClienteRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<ClienteEntity> obtenerClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long id) {
        ClienteEntity cliente = clienteRepository.getReferenceById(id);

        ClienteDTO dto = new ClienteDTO();

        dto.setId(cliente.getId_cliente());

        UsuarioEntity usuario = cliente.getUsuario();
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setContacto(usuario.getContacto());
        dto.setDireccion(cliente.getDireccion());

        return dto;
    }
    
}
