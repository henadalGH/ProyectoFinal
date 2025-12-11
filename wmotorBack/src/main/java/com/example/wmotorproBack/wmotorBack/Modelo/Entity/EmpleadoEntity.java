package com.example.wmotorproBack.wmotorBack.Modelo.Entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "empleado" ,schema = "wmotorpro")
public class EmpleadoEntity {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;


    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private CargosEntity cargo;

    @OneToMany(mappedBy = "empleado")
    private List<OrdenTrabajoEntity> orden = new ArrayList<>();

   
}
