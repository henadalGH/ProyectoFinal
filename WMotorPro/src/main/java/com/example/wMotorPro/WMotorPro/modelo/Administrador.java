package com.example.wMotorPro.WMotorPro.modelo;

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
@Table(name = "admnistrador", schema = "WMotorPro")
public class Administrador {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id:_adminstrador")
    private Long id;


    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
