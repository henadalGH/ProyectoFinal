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

@Data
@Entity
@Table(name = "turno_casual", schema = "wmotorpro")
public class TurnoClienteCasualEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_casual")
    private Long id;

    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @Column(name = "contacto_cliente")
    private String contactoCliente;

    @Column(name = "email_clietne")
    private String email;
    
    @Column(name = "marca_vehiculo")
    private String marcaVehiculo;

    @Column(name = "modelo_vehiculo")
    private String modeloVehiculo;

    @Column(name = "patente_vehiculo")
    private String patenteVehiculo;

    @OneToOne
    @JoinColumn(name = "id_turno")
    private TurnoEntity turno;


}
