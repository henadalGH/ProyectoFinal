package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cliente", schema = "wmotorpro")
public class ClienteEntity {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id_cliente;

    @Column(name = "direccion")
    private String direccion;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity id_usuario;
}
