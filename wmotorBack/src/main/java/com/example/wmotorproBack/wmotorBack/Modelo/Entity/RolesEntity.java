package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "roles", schema = "wmotorpro")
public class RolesEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id_rol;

    @Column(name = "nombre")
    @Enumerated(EnumType.STRING)
    private RolesEnum nombre;

    @OneToMany(mappedBy = "rol")
    private List<UsuarioEntity> usuario;
    
}
