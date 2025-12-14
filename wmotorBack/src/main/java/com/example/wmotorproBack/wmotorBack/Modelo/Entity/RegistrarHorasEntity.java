package com.example.wmotorproBack.wmotorBack.Modelo.Entity;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "registro_horas", schema = "wmotorpro")
public class RegistrarHorasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_hora")
    private Long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "horas_trabajadas")
    private Integer horaTrabajadas;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private EmpleadoEntity empleado;

}
