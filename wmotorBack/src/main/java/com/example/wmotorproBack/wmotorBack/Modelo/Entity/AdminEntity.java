package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "administrador" , schema = "wmotorpro")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Long id_admin;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore 
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore 
    private List<MovimientoFinancieroEntity>  movimiento = new ArrayList<>();

    @OneToMany(mappedBy = "admin")
    @JsonIgnore 
    private List<ReportesEntity> reportes = new ArrayList<>();

    @OneToMany(mappedBy = "admin")
    @JsonIgnore 
    private List<PresupuestoEntity> presupuesto =  new ArrayList<>();
}
