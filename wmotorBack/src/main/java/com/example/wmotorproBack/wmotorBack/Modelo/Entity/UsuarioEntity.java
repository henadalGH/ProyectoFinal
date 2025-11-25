package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario", schema = "wmotorpro", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class UsuarioEntity { 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    @Column String password;

    @Column
    private String contacto;

    @Column
    private Boolean estado;


    @OneToOne
    @JoinColumn(name = "id_rol")
    private RolesEntity rol;
}
